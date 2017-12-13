package by.vbalanse.vaadin.training;

import by.vbalanse.model.training.TrainingTypeEnum;
import com.vaadin.data.util.converter.Converter;

import java.util.Date;
import java.util.Locale;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
public class StringToTrainingTypeEnumConverter implements Converter<String, TrainingTypeEnum> {
  public TrainingTypeEnum convertToModel(String value, Class<? extends TrainingTypeEnum> targetType, Locale locale) throws ConversionException {
    return TrainingTypeEnum.parseTrainingType(value);
  }

  public String convertToPresentation(TrainingTypeEnum value, Class<? extends String> targetType, Locale locale) throws ConversionException {
    return value.getTitle();
  }

  public Class<TrainingTypeEnum> getModelType() {
    return TrainingTypeEnum.class;
  }

  public Class<String> getPresentationType() {
    return String.class;
  }
}
