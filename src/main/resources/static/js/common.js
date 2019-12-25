var Common = {
  form: function (url, data) {
    var form = $('<form method="post"></form>').attr('action', url).appendTo('body');
    for (var k in data) {
      form.append($('<input type="hidden">').attr('name', k).val(data[k]));
    }
    return form;
  },
  load: function(arr, success) {
    var loadedCount = 0;
    var arrLength = (arr.css ? arr.css.length : 0) + (arr.js ? arr.js.length : 0);
    for (var i in arr.css) {
      var obj = $('<link>').attr({rel: 'stylesheet', href: arr.css[i]}).appendTo('head').get(0);
      obj.onload = obj.onreadystatechange = function() {
        if (!this.readyState || this.readyState === 'loaded' || this.readyState === 'complete') {
          ++loadedCount;
          complete();
        }
      }
    }
    for (var i in arr.js) {
      $.getScript(arr.js[i], function() {
        ++loadedCount;
        complete();
      });
    }
    function complete() {
      if (loadedCount === arrLength) {
        success();
      }
    }
  }
};