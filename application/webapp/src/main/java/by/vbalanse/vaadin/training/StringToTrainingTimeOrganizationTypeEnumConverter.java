package by.vbalanse.vaadin.training;

import by.vbalanse.model.training.TrainingTimeOrganizationTypeEnum;
import com.vaadin.data.util.converter.Converter;

import java.util.Locale;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
public class StringToTrainingTimeOrganizationTypeEnumConverter implements Converter<Object, TrainingTimeOrganizationTypeEnum> {
  public TrainingTimeOrganizationTypeEnum convertToModel(Object value, Class<? extends TrainingTimeOrganizationTypeEnum> targetType, Locale locale) throws ConversionException {
    return TrainingTimeOrganizationTypeEnum.parseTrainingTimeOrganizationType((String) value);
  }

  public String convertToPresentation(TrainingTimeOrganizationTypeEnum value, Class<? extends Object> targetType, Locale locale) throws ConversionException {
    if (value == null) {
      return "";
    }
    return value.getTitle();
  }

  public Class<TrainingTimeOrganizationTypeEnum> getModelType() {
    return TrainingTimeOrganizationTypeEnum.class;
  }

  public Class<Object> getPresentationType() {
    return Object.class;
  }
}
