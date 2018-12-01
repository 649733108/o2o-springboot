$(function () {
   var shopId = getQueryString('shopId');
   var shopInfoUrl = '/shopadmin/getshopmanagementinfo?shopId=' + shopId;

   $.getJSON(shopInfoUrl,function (data) {
       if (data.redirect) {
           window.location.href = data.url;
       }else {
           if ( data.shopId != null&&data.shopId != undefined ) {
               shopId = data.shopId;
           }
           $('#shopInfo').attr('href','/shopadmin/shopoperation?shopId=' + shopId);
           $('#product-category').attr('href','/shopadmin/productcategorymanagement' );
       }
   });
});