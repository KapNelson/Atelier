$(function () {

    $('.slider__inner, .news__slider-inner').slick({
        nextArrow: '<button type="button" class="slick-btn slick-next"></button>',
        prevArrow: '<button type="button" class="slick-btn slick-prev"></button>',
        infinite: false
    });

    $('select').styler();

    $('.header__btn-menu').on('click', function () {
        $('.menu ul').slideToggle();
    });
});

(function ($) {
    $(window).on("load", function () {
        $('.menu a').mPageScroll2id();
    });
})(jQuery);

$('#registration').submit(function (e) {
    e.preventDefault();

    const login = document.getElementById("login_new");
    const password = document.getElementById("password_new");
    const repassword = document.getElementById("repassword_new");
    var message = document.getElementById("message_reg");

    const regexpPassword = /(?=.*[A-Z])^[0-9a-zA-Z]*$/g;
    const regexpLogin = /^[0-9a-zA-Z]*$/g;

    if (!login.value || !password.value || !repassword.value) {
        message.innerHTML = "Всі поля повинні бути обов'язково заповнені!";
    } else if (login.value.length < 4 || login.value.length > 32) {
        message.innerHTML = "Логін повинен складатись від 4 до 32 символів!";
    } else if (!regexpLogin.test(login.value)) {
        message.innerHTML = "Логін повинен містити лише цифри та латиницю!";
    } else if (password.value.length < 8 || password.value.length > 32) {
        message.innerHTML = "Пароль повинен складатись від 8 до 32 символів!";
    } else if (!regexpPassword.test(password.value)) {
        message.innerHTML = "Пароль повинен містити лише цифри, латиницю, а також, хоча б одну велику літеру!";
    } else if (password.value !== repassword.value) {
        message.innerHTML = "Паролі не співпадають!";
    } else {
        $.ajax({
            url: '/reg',
            type: 'post',
            data: $('#registration').serialize(),
            success: function () {
                message.innerHTML = "Реєстрація успішна!";
            },
            statusCode: {
                409: function () {
                    message.innerHTML = "Користувач з таким логіном вже існує!";
                }
            }
        });
    }
});

$('#login').submit(function (e) {
    e.preventDefault();

    const username = document.getElementById("username");
    const password = document.getElementById("password");
    var message = document.getElementById("message");

    if (!username.value || !password.value) {
        message.innerHTML = "Всі поля повинні бути обов'язково заповнені!";
    } else {
        $.ajax({
            url: '/login',
            type: 'post',
            data: $('#login').serialize(),
            success: function () {
                message.innerHTML = "Вхід успішний!";
                window.location.href = '/';
            },
            statusCode: {
                401: function () {
                    message.innerHTML = "Логін або пароль - невірні!";
                }
            }
        });
    }
});

function onBodyLoad() {
    if (window.location.href.endsWith('#login')) {
        $("#login_link").fancybox().trigger('click');
    }
}

$('#review').submit(function (e) {
    e.preventDefault();

    const name = document.getElementById("review_name");
    const phone = document.getElementById("review_phone");
    const text = document.getElementById("review_text");
    var message = document.getElementById("message_review");

    const regexpPhone = /^(\s*)?(\+)?([- _():=+]?\d[- _():=+]?){10,14}(\s*)?$/;
    const regexpName = /^[А-Яа-яІіЇїЄє]*$/g;

    if (!name.value || !phone.value || !text.value) {
        message.innerHTML = "Всі поля повинні бути обов'язково заповнені!";
    } else if (!regexpName.test(name.value)) {
        message.innerHTML = "ПІБ повинно містити лише кирилицю!";
    } else if (!regexpPhone.test(phone.value)) {
        message.innerHTML = "Номер телефону має невірний формат!";
    } else if (text.value.length > 1000) {
        message.innerHTML = "Максимальний розмір відгуку - 1000 символів!";
    } else {
        $.ajax({
            url: '/addreview',
            type: 'post',
            data: $('#review').serialize(),
            success: function () {
                message.innerHTML = "Відгук відправлений на модерацію!";

            }
        });
    }
});


