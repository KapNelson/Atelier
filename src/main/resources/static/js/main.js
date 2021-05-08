let EPPZScrollTo =
    {
        /**
         * Helpers.
         */
        documentVerticalScrollPosition: function () {
            if (self.pageYOffset) return self.pageYOffset; // Firefox, Chrome, Opera, Safari.
            if (document.documentElement && document.documentElement.scrollTop) return document.documentElement.scrollTop; // Internet Explorer 6 (standards mode).
            if (document.body.scrollTop) return document.body.scrollTop; // Internet Explorer 6, 7 and 8.
            return 0; // None of the above.
        },

        viewportHeight: function () {
            return (document.compatMode === "CSS1Compat") ? document.documentElement.clientHeight : document.body.clientHeight;
        },

        documentHeight: function () {
            return (document.height !== undefined) ? document.height : document.body.offsetHeight;
        },

        documentMaximumScrollPosition: function () {
            return this.documentHeight() - this.viewportHeight();
        },

        elementVerticalClientPositionById: function (id) {
            let element = document.getElementById(id);
            let rectangle = element.getBoundingClientRect();
            return rectangle.top;
        },

        /**
         * Animation tick.
         */
        scrollVerticalTickToPosition: function (currentPosition, targetPosition) {
            let filter = 0.05;
            let fps = 60;
            let difference = parseFloat(targetPosition) - parseFloat(currentPosition);

            // Snap, then stop if arrived.
            let arrived = (Math.abs(difference) <= 0.5);
            if (arrived) {
                // Apply target.
                scrollTo(0.0, targetPosition);
                return;
            }

            // Filtered position.
            currentPosition = (parseFloat(currentPosition) * (1.0 - filter)) + (parseFloat(targetPosition) * filter);

            // Apply target.
            scrollTo(0.0, Math.round(currentPosition));

            // Schedule next tick.
            setTimeout("EPPZScrollTo.scrollVerticalTickToPosition(" + currentPosition + ", " + targetPosition + ")", (1000 / fps));
        },

        /**
         * For public use.
         *
         * @param id The id of the element to scroll to.
         * @param padding Top padding to apply above element.
         */
        scrollVerticalToElementById: function (id, padding) {
            let element = document.getElementById(id);
            if (element == null) {
                console.warn('Cannot find element with id \'' + id + '\'.');
                return;
            }

            let targetPosition = this.documentVerticalScrollPosition() + this.elementVerticalClientPositionById(id) - padding;
            let currentPosition = this.documentVerticalScrollPosition();

            // Clamp.
            let maximumScrollPosition = this.documentMaximumScrollPosition();
            if (targetPosition > maximumScrollPosition) targetPosition = maximumScrollPosition;

            // Start animation.
            this.scrollVerticalTickToPosition(currentPosition, targetPosition);
        },

        /**
         * For public use.
         *
         * @param name The name of the first element to scroll to.
         * @param padding Top padding to apply above element.
         */
        scrollVerticalToElementByName: function (name, padding) {
            let element = document.getElementsByName(name);
            if (element == null) {
                console.warn('Cannot find element with name \'' + name + '\'.');
                return;
            }

            let targetPosition = this.documentVerticalScrollPosition() + this.elementVerticalClientPositionById(id) - padding;
            let currentPosition = this.documentVerticalScrollPosition();

            // Clamp.
            let maximumScrollPosition = this.documentMaximumScrollPosition();
            if (targetPosition > maximumScrollPosition) targetPosition = maximumScrollPosition;

            // Start animation.
            this.scrollVerticalTickToPosition(currentPosition, targetPosition);
        }

    };

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

/*(function($) {
    $(window).on("load", function() {
        $('.menu a').mPageScroll2id();
    });
})(jQuery);*/

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
        },
        statusCode: {
            401: function () {
                document.getElementById("message").innerHTML = "Логін або пароль - невірні!";
            }
        }
    });
});