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
    $.ajax({
        url: '/reg',
        type: 'post',
        data: $('#registration').serialize(),
        success: function () {
            document.getElementById("message_reg").innerHTML = "Реєстрація успішна!";
        },
        statusCode: {
            401: function () {
                document.getElementById("message_reg").innerHTML = "Паролі не співпадають!";
            },
            409: function () {
                document.getElementById("message_reg").innerHTML = "Користувач з таким логіном вже існує!";
            }
        }
    });
});

$('#login').submit(function (e) {
    e.preventDefault();
    $.ajax({
        url: '/login',
        type: 'post',
        data: $('#login').serialize(),
        success: function () {
            document.getElementById("message").innerHTML = "Вхід успішний!";
            window.location.href = '/';
        },
        statusCode: {
            401: function () {
                document.getElementById("message").innerHTML = "Логін або пароль - невірні!";
            }
        }
    });
});

function onBodyLoad() {
    if (window.location.href.endsWith('#login')) {
        $("#login_link").fancybox().trigger('click');
    }
}
