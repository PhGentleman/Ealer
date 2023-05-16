
var count=1;
$("#addTag").click(function (){
    $("#addTag").before("<li class=\"list-group-item\">"+
        "<div class=\"form-group\">"+
        "<label for=\"tname"+count+"\">标签名</label>"+
        "<input type=\"text\" class=\"form-control\" name=\"tname"+count+"\" id=\"tname"+count+"\">"+
        "</div>"+
        "</li>");
    count++;
})