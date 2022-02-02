$.fancyConfirm = function( opts ) {
    opts  = $.extend( true, {
        title     : 'Are you sure?',
        message   : '',
        okButton  : 'OK',
        noButton  : 'Cancel',
        callback  : $.noop
    }, opts || {} );

    $.fancybox.open({
            type : 'html',
            src  :
                '<div class="fc-content">' +
                '<h3>' + opts.title   + '</h3>' +
                '<p>'  + opts.message + '</p>' +
                '<p class="tright">' +
                '<a data-value="0" data-fancybox-close>' + opts.noButton + '</a>' +
                '<button data-value="1" data-fancybox-close class="btn">' + opts.okButton + '</button>' +
                '</p>' +
                '</div>',
            opts : {
                animationDuration : 200,
                animationEffect   : 'material',
                modal : true,
                baseTpl :
                    '<div class="fancybox-container fc-container" role="dialog" tabindex="-1">' +
                    '<div class="fancybox-bg"></div>' +
                    '<div class="fancybox-inner">' +
                    '<div class="fancybox-stage"></div>' +
                    '</div>' +
                    '</div>',
                afterClose : function( instance, current, e ) {
                    var button = e ? e.target || e.currentTarget : null;
                    var value  = button ? $(button).data('value') : 0;

                    opts.callback( value );
                }
            }
        }
    );
}

// Step 2: Start using it!
// =======================

$("#test_confirm").click(function() {

    // Open customized confirmation dialog window
    $.fancyConfirm({
        title     : "Use Google's location service?",
        message   : "Let Google help apps determine location. This means sending anonymous location data to Google, even when no apps are running",
        okButton  : 'Agree',
        noButton  : 'Disagree',
        callback  : function (value) {
            if (value) {
                $("#test_confirm_rez").html("Let's do this!");

            } else {
                $("#test_confirm_rez").html("Maybe later.");
            }
        }
    });

});