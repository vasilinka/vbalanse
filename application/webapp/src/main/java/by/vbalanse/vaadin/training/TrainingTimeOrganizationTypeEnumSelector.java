/**
 * Copyright 2009-2013 Oy Vaadin Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package by.vbalanse.vaadin.training;

import by.vbalanse.model.training.TrainingTimeOrganizationTypeEnum;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomField;

import java.util.Arrays;

/**
 * A custom field that allows selection of a role.
 */
public class TrainingTimeOrganizationTypeEnumSelector extends CustomField<TrainingTimeOrganizationTypeEnum> {
  private ComboBox trainingTimeType = new ComboBox();

  public TrainingTimeOrganizationTypeEnumSelector() {
    setCaption("Role");
    BeanItemContainer<TrainingTimeOrganizationTypeEnum> newDataSource = new BeanItemContainer<TrainingTimeOrganizationTypeEnum>(TrainingTimeOrganizationTypeEnum.class);
    //newDataSource.setBeanIdProperty("code");
    newDataSource.addAll(Arrays.asList(TrainingTimeOrganizationTypeEnum.values()));
//    newDataSource.addContainerProperty("title", String.class, "");
//    newDataSource.addContainerProperty("code", String.class, "");
    trainingTimeType.setContainerDataSource(newDataSource);
    //trainingTimeType.setConverter(new StringToTrainingTimeOrganizationTypeEnumConverter());


    trainingTimeType.setItemCaptionPropertyId("title");
    trainingTimeType.setImmediate(true);

    trainingTimeType.addListener(new ValueChangeListener() {
      public void valueChange(
          Property.ValueChangeEvent event) {
                /*
                 * Modify the actual value of the custom field.
                 */
        TrainingTimeOrganizationTypeEnum code = (TrainingTimeOrganizationTypeEnum) trainingTimeType.getValue();
        setValue(code, false);
      }
    });
  }

  @Override
  protected Component initContent() {
    CssLayout cssLayout = new CssLayout();
    cssLayout.addComponent(trainingTimeType);
    return cssLayout;

  }

  @Override
  public Class<? extends TrainingTimeOrganizationTypeEnum> getType() {
    return TrainingTimeOrganizationTypeEnum.class;
  }

  public void setValue(TrainingTimeOrganizationTypeEnum newValue) throws ReadOnlyException,
      Converter.ConversionException {
    super.setValue(newValue);
    setTrainingTimeOrganization(newValue);
  }

  private void setTrainingTimeOrganization(TrainingTimeOrganizationTypeEnum role) {
    if (role != null) {
      trainingTimeType.setValue(role);
    }
  }

  @Override
  public void setPropertyDataSource(Property newDataSource) {
    super.setPropertyDataSource(newDataSource);
    TrainingTimeOrganizationTypeEnum value = (TrainingTimeOrganizationTypeEnum) newDataSource.getValue();
    setTrainingTimeOrganization(value);
  }

}
