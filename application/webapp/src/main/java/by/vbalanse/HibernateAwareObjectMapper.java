package by.vbalanse;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import org.codehaus.jackson.map.DeserializationConfig;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
public class HibernateAwareObjectMapper extends ObjectMapper {

  public HibernateAwareObjectMapper() {
    Hibernate4Module module = new Hibernate4Module();
    module.enable(Hibernate4Module.Feature.SERIALIZE_IDENTIFIER_FOR_LAZY_NOT_LOADED_OBJECTS);
    module.enable(Hibernate4Module.Feature.FORCE_LAZY_LOADING);
    //configure(SerializationFeature.CLOSE_CLOSEABLE, true);
    configure(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED, false);
    //configure(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED, true);
    //configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    configure(
        SerializationFeature.EAGER_SERIALIZER_FETCH, false);
    //module.enable(Hibernate4Module.Feature.REQUIRE_EXPLICIT_LAZY_LOADING_MARKER);
    registerModule(module);
  }
}
