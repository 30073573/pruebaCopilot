package adeo.lmes.pruebaCopilot.dominio.puertos;

import adeo.lmes.pruebaCopilot.dominio.entidades.Usuario;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Puerto: Interfaz de repositorio de Usuario
 * Define el contrato para la persistencia de datos
 */
public interface UsuarioRepository {
    
    Mono&lt;Usuario&gt; findById(Long id);
    
    Flux&lt;Usuario&gt; findAll();
    
    Mono&lt;Usuario&gt; save(Usuario usuario);
    
    Mono&lt;Void&gt; deleteById(Long id);
}