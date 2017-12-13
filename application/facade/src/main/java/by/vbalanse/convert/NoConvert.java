package by.vbalanse.convert;

import java.lang.annotation.*;

/**
 * Created by Vasilina on 03.03.2015.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NoConvert {
}
