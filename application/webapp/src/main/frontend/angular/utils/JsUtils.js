/**
 * Created by Vasilina on 26.07.2015.
 */
JsUtils = (function () {
  return function () {
    this.toBoolean = function(value) {
      if (value && value.length !== 0) {
        var v = ("" + value).toLowerCase();
        value = !(v == 'f' || v == '0' || v == 'false' || v == 'no' || v == 'n' || v == '[]');
      } else {
        value = false;
      }
      return value;
    };
  }
})();