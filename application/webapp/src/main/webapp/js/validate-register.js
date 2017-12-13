jQuery(document).ready(function () {
  /*$( "#password" ).rules('add', {
   required: true,
   minlength: 5
   });*/
  jQuery.extend(jQuery.validator.messages, {
    required: "Поле обязательное.",
    max: jQuery.validator.format("Поле не должно быть больше {0}."),
    min: jQuery.validator.format("Пожалуйста введите значение больше {0}."),
    email: jQuery.validator.format("Введите корректный email.")
  });

  $('#registerForm').validate({
    //debug: true,
    /*$('#username').rules("add",
     {minlength: 5}
     );*/
    rules: {

      username: {
        required: true,
        email: true
      },
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
        /*minlength: 5,
         equalTo: "#password"*/
      }
      //required: "Поле обязательное",
      //equalTo: jQuery.format("Пожалуйста подтвердите пароль")
    }
//return false;

  });
});
