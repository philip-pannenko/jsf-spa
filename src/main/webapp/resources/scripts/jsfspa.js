$(function() {

  var History = window.History;
  var State = History.getState();

  if (History.enabled) {
    State = History.getState();
    History.pushState(null, null, State.urlPath);
  } else {
    return false;
  }

  History.Adapter.bind(window, 'statechange', function() {
    var State = History.getState();
    History.log('statechange:', State.data, State.title, State.url);

    if (!State.data.server) {

      $.ajax({
        url : State.url,
        headers : {Fragment : "true"},
        success : function(data, a, b, c) {
          if(b.getResponseHeader('Fragement') === 'false') {
            window.location.href = '/jsfspa/login.xhtml';
          } else {
            $("#testMe").html(data);
          }
        },
        error : function(jqXHR) {
          debugger;
          // Put whatever you need to do if the query fails here.
        }
      });
    }
  });
  
  $('body').on('click', 'a', function(e) {

    var val = $(this).attr('href');
    if (!val)
      return false;

    if (val.indexOf("#") > -1) {
      val = val.split("#")[1];
    }

    History.pushState({fragment : val}, null, val);
    return false;
  });

  var $loading = $('#loadingDiv').hide();
  $(document).ajaxStart(function() {
    $loading.show();
  }).ajaxStop(function() {
    $loading.hide();
  });

  
});
