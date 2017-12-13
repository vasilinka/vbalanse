package by.vbalanse.convert;

import java.util.List;

/**
 * Created by Vasilina on 19.03.2015.
 */
public interface Transferer {
  <From, To> List<To> transferList(Class from, Class to, List<From> objects);
  <From, To> To transfer(Class from, Class to, From obj);
}
