jQuery(document).ready(function () {
  jQuery.extend(jQuery.validator.messages, {
    required: "Поле обязательное.",
    max: jQuery.validator.format("Поле не должно быть больше {0}."),
    min: jQuery.validator.format("Пожалуйста введите значение больше {0}."),
    email: jQuery.validator.format("Введите корректный email.")
  });

  $('#changePasswordForm').validate({
    rules: {

      password: {
        required: true,
        minlength: 5
      },
      password_confirm: {
        required: true,
        minlength: 5,
        equalTo: "#password"
      }
    },
    messages: {
      password_confirm: {
        equalTo: "Пожалуйста, подтвердите пароль",
        required: "Поле обязательное"
      }
    }

  });
});
