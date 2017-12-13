jQuery(document).ready(function () {
  jQuery.extend(jQuery.validator.messages, {
    required: "Поле обязательное.",
    max: jQuery.validator.format("Поле не должно быть больше {0}."),
    min: jQuery.validator.format("Пожалуйста введите значение больше {0}."),
    email: jQuery.validator.format("Введите корректный email.")
  });

  $('#forgotPasswordForm').validate({
    rules: {

      username: {
        email: true,
        required: true
      }
    }

  });
});
