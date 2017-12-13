package by.vbalanse.vaadin.training;

import by.vbalanse.model.training.TrainingTimeOrganizationTypeEnum;
import by.vbalanse.model.training.TrainingTypeEnum;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.data.util.converter.DefaultConverterFactory;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
public class MyConverterFactory extends DefaultConverterFactory {
  @Override
  protected <PRESENTATION, MODEL> Converter<PRESENTATION, MODEL> findConverter(
      Class<PRESENTATION> presentationType, Class<MODEL> modelType) {
    // Handle String <-> Double
//    if (presentationType == TrainingTypeEnum.class && modelType == String.class) {
//      return (Converter<PRESENTATION, MODEL>) new TrainingTypeEnumToStringConverter();
//    }
//    if (presentationType == TrainingTimeOrganizationTypeEnum.class && modelType == String.class) {
//      return (Converter<PRESENTATION, MODEL>) new StringToTrainingTimeOrganizationTypeEnumConverter();
//    }
    // Let default factory handle the rest
    return super.findConverter(presentationType, modelType);
  }
}
