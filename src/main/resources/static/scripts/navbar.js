$(document).ready(function(){
    $(window).scroll(function(){
        let scroll = $(window).scrollTop();
        if (scroll < 300) {
            $(".bkg").css("background", "#2D3E4B");
            $(".bkg div ul li a")
                .css("color", "#ffffff");
            $(".bkg div a i")
                .css("color", "#ffffff");
            $(".bkg div a")
                .css("color", "#ffffff");
        }

        else{
            $(".bkg")
                .css("background", "#ffffff");
            $(".bkg div ul li a")
                .css("color", "#2D3E4B");
            $(".bkg div a i")
                .css("color", "#2D3E4B");
            $(".bkg div a")
                .css("color", "#2D3E4B");
        }
    })
})
