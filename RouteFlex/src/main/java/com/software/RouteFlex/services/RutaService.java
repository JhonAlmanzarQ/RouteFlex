package com.software.RouteFlex.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.software.RouteFlex.models.PaqueteEnvio;
import com.software.RouteFlex.models.Ruta;
import com.software.RouteFlex.models.Usuario;
import com.software.RouteFlex.repositories.RutaRepository;
import com.software.RouteFlex.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RutaService implements IRutaService {

    @Autowired
    RutaRepository rutaRepository;
    @Autowired
    UsuarioRepository usuarioRepository;

    private static final String GOOGLE_MAPS_API_KEY = "AIzaSyCUVugVZe_pFpelUF-2ffL4AGnnqSNUt18";
    private static final String GEOCODING_URL = "https://maps.googleapis.com/maps/api/geocode/json?address=%s&key=" + GOOGLE_MAPS_API_KEY;
    private static final String DIRECTIONS_URL = "https://maps.googleapis.com/maps/api/directions/json?origin=%s&destination=%s&waypoints=optimize:true|%s&key=" + GOOGLE_MAPS_API_KEY;


    @Override
    public List<Ruta> listarRutaUsuario(Long id) {
        return rutaRepository.findRutaByUsuario(id);
    }

    @Override
    public Ruta crearRuta(List<PaqueteEnvio> paqueteEnvio) {

        Usuario usuario = paqueteEnvio.get(0).getUsuario();

        // Lista para almacenar las coordenadas obtenidas
        List<String> coordenadas = new ArrayList<>();

        try {
            // Obtener coordenadas de cada dirección usando la Geocoding API
            for (PaqueteEnvio paquete : paqueteEnvio) {
                String direccionCodificada = URLEncoder.encode(paquete.getDireccion(), "UTF-8");
                String urlGeocoding = String.format(GEOCODING_URL, direccionCodificada);

                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<String> response = restTemplate.getForEntity(urlGeocoding, String.class);

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response.getBody());

                if ("OK".equals(rootNode.get("status").asText())) {
                    JsonNode locationNode = rootNode.get("results").get(0).get("geometry").get("location");
                    String lat = locationNode.get("lat").asText();
                    String lng = locationNode.get("lng").asText();

                    coordenadas.add(lat + "," + lng);
                }
            }

            // Validar que tenemos al menos dos coordenadas para calcular la ruta
            if (coordenadas.size() < 2) {
                throw new IllegalArgumentException("Se necesitan al menos dos coordenadas para calcular una ruta.");
            }

            // Preparar parámetros para la Directions API
            String origen = coordenadas.get(0);
            String destino = coordenadas.get(coordenadas.size() - 1);
            String waypoints = coordenadas.size() > 2 ? String.join("|", coordenadas.subList(1, coordenadas.size() - 1)) : "";

            String urlDirections = String.format(DIRECTIONS_URL, origen, destino, waypoints);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseDirections = restTemplate.getForEntity(urlDirections, String.class);

            JsonNode rootNodeDirections = new ObjectMapper().readTree(responseDirections.getBody());

            if ("OK".equals(rootNodeDirections.get("status").asText())) {
                JsonNode route = rootNodeDirections.get("routes").get(0);
                String overviewPolyline = route.get("overview_polyline").get("points").asText();

                // Crear la ruta y guardarla
                Ruta nuevaRuta = new Ruta();
                nuevaRuta.setOverviewPolyline(overviewPolyline);
                nuevaRuta.setCoordenadas(coordenadas);
                nuevaRuta.setUsuario(usuario);

                rutaRepository.save(nuevaRuta);  // Guardar la nueva ruta en la base de datos

                return nuevaRuta;  // Retornar el objeto Ruta completo
            } else {
                throw new RuntimeException("Error en Directions API");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Ocurrió un error al calcular la ruta.", e);
        }
    }

    @Override
    public List<Ruta> listarRutas() {
        return rutaRepository.findAll();
    }

    @Override
    public void eliminarRuta(Long id) {
        if (rutaRepository.existsById(id)){
            rutaRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Ruta no encontrada");
        }
    }

}
