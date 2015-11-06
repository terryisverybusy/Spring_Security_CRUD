<%@include file="taglib_includes.jsp"%>




<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>


<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>


<script>

function go(url)
{
	window.location = url;
}
</script>



<script type="text/javascript" src="contacts.js"></script>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/r/zf-5.5.2/jqc-1.11.3,dt-1.10.8/datatables.min.css"/>

    <script type="text/javascript" src="https://cdn.datatables.net/r/zf-5.5.2/jqc-1.11.3,dt-1.10.8/datatables.min.js"></script>
    <script type="text/javascript" charset="utf-8">
        $(document).ready(function() {
  $('#example').dataTable( {
        "iDisplayLength": 50
    } );
} );
    </script>



</head>
<body>

<div class="row">
    <div class="large-12 columns">
<input type="button" value="Main Page" class="btn pull-left" onclick="javascript:go('home.do');" />

        <table id="example" class="display" cellspacing="0" width="100%">
<thead>
<tr><th>Id</th><th>Name</th><th>Address</th><th>Mobile</th><th></th></tr></thead>
<tbody>
	<c:if test="${empty SEARCH_CONTACTS_RESULTS_KEY}">
				<tr>
					<td colspan="4">No Results found</td>
				</tr>
			</c:if>
			<c:if test="${! empty SEARCH_CONTACTS_RESULTS_KEY}">
				<c:forEach var="contact" items="${SEARCH_CONTACTS_RESULTS_KEY}">
					<tr>
						<td><c:out value="${contact.id}"></c:out></td>
						<td><c:out value="${contact.name}"></c:out></td>
						<td><c:out value="${contact.address}"></c:out></td>
						<td><c:out value="${contact.mobile}"></c:out></td>
						<td>&nbsp;<a href="updateContact.do?id=${contact.id}">Edit</a>
							&nbsp;&nbsp;<a
							href="javascript:deleteContact('deleteContact.do?id=${contact.id}');">Delete</a>
						</td>
					</tr>
				</c:forEach>
			</c:if>
</tbody></table>

<input type="button" value="Insert New Contact" class="btn pull-right" onclick="javascript:go('saveContact.do');" />

    </div>
</div>






<script type="text/javascript">
    // For demo to fit into DataTables site builder...
    $('#example')
            .removeClass( 'display' )
            .addClass('tdisplay');
</script>



</body>
</html>







