package by.vbalanse.convert;

import org.springframework.web.bind.annotation.ValueConstants;

import java.lang.annotation.*;

/**
 * Created by Vasilina on 03.03.2015.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DateConvert {
  String name() default ValueConstants.DEFAULT_NONE;

  String format() default "";

}
