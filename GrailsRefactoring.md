# Introduction #

First draft of some refactoring done on Grails projects. Created an "InstanceController", an "InstanceService" and "crud.gsp", "list.gsp" to cut down editing and code duplication across domain classes.

# Details #

This is the code for the blog at http://fjanon.blogspot.com/2009/10/over-my-last-few-grails-projects.html


---

in /domainclass/Streettype.groovy
```
class Streettype {
	String name

	static constraints = {
		name('unique': true, 'nullable': false, 'maxSize': 15)
		}

	static mapping = {
		version false
	}

public String toMessage()	// Needed for the InstanceController to display error messages
	{
	 return this.toString()
	}

public String toString()
	{
	 def str = (name?: '-no streettype name-')
	 return str
	}
}
```

---

in Bootstrap.groovy:
```
class BootStrap {

def streettypeService

def init =
	{
	 streettypeService.setup()
	}
}
```

---

in /config/Constants.groovy
```
public class Constants {
//  Used in the controllers and 'crud.gsp'
public static final SHOW = 'show'
public static final EDIT = 'edit'
public static final CREATE = 'create'
}
```

---

in /controllers/InstanceController.groovy
```
// Generic controller
abstract class InstanceController {

def service = null // MUST be initialized in the subclass
def instancename = null // MUST be initialized in the subclass

def beforeInterceptor = {
	setup()
}

abstract def setup();

def index = { index() }
def index () { list() }	// a bit faster, no need for the redirect

def list = { list() }
def list()
	{
	 // if there is an 'id' in the 'params', use it to highlight that item in the list
	 def instance = service.getById(params.id)
	 // 'selected' is used to highlight the instance last edited/saved. Caution: uniform can be null when the list is called by list or index
	 render('view': 'list', 'model': ['instancelist': service.list(), 'selectedid': instance?.id, 'instancename': instancename])
	}

def show = { show() }
def show()
	{
	 def instance = service.getById(params.id)
	 if (!instance)
		{
		 flash.message = "$instancename not found with id ${params.id}"
		 redirect('action': 'list')
		}
	 render('view': 'crud', 'model': ['instance': instance, 'edit': false, 'mode': Constants.SHOW, 'instancename': instancename])
	}

def delete = { delete() }
def delete()
	{
	def instance = service.getById(params.id)
	def name = instance?.toMessage()	// Save it for use in the message after the instance is deleted
	if (!instance)
		{
		 flash.message = name?:'Element' + " not found with id '${params.id}'"
		 redirect('action': 'list')
		 return
		}

	if (service.metaClass.respondsTo(service, "isUsed") && service.isUsed(instance))
		{
		 flash.message = name?:'Element' + ' is still used by other elements, it cannot be deleted.'
		 redirect('action': 'show', 'id': params.id)
		 return
		}
	 if (service.delete(instance))
		{
		 flash.message = "Element ${name?'\'' + name + '\'':''} deleted"
		 redirect('action': 'list')
		 return
		}
	 else
		{
		 flash.message = "Element ${name?'$name':''} could not be deleted"
		 redirect('action': 'show', 'id': params.id)
		 return
		}
	}

def edit = { edit() }
def edit()
	{
	def instance = service.getById(params.id)
	if (!instance)
		{
		 flash.message = "Element not found with id ${params.id}"
		 redirect('action': 'list')
		}
	else
		{
		 render('view': 'crud', 'model': ['instance': instance, 'edit': true, 'mode': Constants.EDIT, '$instancename': instancename])
		}
	}

def update = { update() }
def update()
	{
	def instance = service.getById(params.id)
	if (!instance)
		{
		 flash.message = "Element not found with id ${params.id}"
		 redirect('action': 'list')
		 return
		}

	instance.properties = params
	bindmore(instance, params)

	instance = service.update(instance)
	if (instance.hasErrors())
		{
		 render('view': 'crud', 'model': ['instance': instance, 'edit': true, 'mode': Constants.EDIT, 'instancename': instancename])
		}
	else
		{
		 flash.message = "$instancename '${instance.toMessage()}' updated"
		 redirect('action': 'list', 'id': instance?.id)	// The id used to highlight the instance in the list
		}
	}

def void bindmore(instance, params)
	{
	 // Overriden by subclasses if extra binding is needed
	 // example: if (params.defaultcolor) instance.defaultcolor = true	// Bind the checkbox
	}

def create = { create() }
def create()
	{
	 def instance = service.newinstance()
	 instance.properties = params
	 render('view': 'crud', 'model': ['instance': instance, 'edit': true, 'mode': Constants.CREATE, 'instancename': instancename])
	}

def save = { save() }
def save()
	{
	def instance = service.save(params)
	if (instance.hasErrors())
		{
		 // redisplay the 'crud.gsp' view until the user corrects the validation issues
		 render('view': 'crud', 'model': ['instance': instance, 'edit': true, 'mode': Constants.CREATE, 'instancename': instancename])
		}
	else
		{
		 flash.message = "$instancename '${instance.toMessage()}' created"
		 redirect('action': 'list', 'id': instance?.id)	// The id passed in the URL is used to highlight the instance in the list
		}
	}

def formactions = { formactions() }
def formactions()
	{
	 // we use only one view for 'show', 'create' and 'update': the 'crud.gsp' view
	 // We come here from 'crud.gsp': captures all the button actions: 'save', 'delete', 'cancel'
	 // the action to perform is in params.operation, since it is the name of the buttons
	 // we cannot use the buttons name 'action' since it's used by Grails
	 // params: [id:4, operation:save, description:outdoors, name:t-shirt, action:formactions, defaultuniform:on, controller:uniform]
//	 println(">>>> InstanceController formactions params: $params")
	 // this method is called from 'crud.gsp' with the different actions: 'save', 'delete', 'cancel'

	 def instance = service.getById(params.id)
	 if (params.operation == 'cancel')
		{
		 if ((params.mode == Constants.EDIT) && params.id)
			{
			 redirect('action': 'show', 'id': params.id)
			 return
			}
		 // redirect 'cancel' to the list view by default
		 redirect('action': 'list', 'id': instance?.id)	// The id passed in the URL is used to highlight the instance in the list
		 return
		}

	 if (params.operation == 'save')
		{
		 if (params.id)	// if it's an existing instance to save
			{
			 update()
			}
		 else
			{
			 save()	// new instance to save
			}
		 return
		}

	 if (params.operation == 'edit') { edit() ; return }
	 if (params.operation == 'delete') { delete() ; return }

	 // We should never get here...
	}
}
```

---

in /controllers/StreettypeController.groovy
```
class StreettypeController extends InstanceController {

def streettypeService	// injected by Grails

// the delete, save and update actions only accept POST requests
static allowedMethods = ['delete': 'POST', 'save': 'POST', 'update': 'POST']

def setup()
	{
	 service = streettypeService
	 instancename = 'Street Type'	// for display
	}
}
```

---

in /views/Streettype/crud.gsp
```
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
		<!-- Streettype/crud.gsp -->
        <title>${instancename} Details</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">${instancename} List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New ${instancename}</g:link></span>
        </div>
        <div class="body">
            <h1>${title}</h1>

            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${instance}">
            <div class="errors">
                <g:renderErrors bean="${instance}" as="list" />
            </div>
            </g:hasErrors>
			<%-- the 'formactions' closure is called in 'InstanceController' --%>
            <g:form action="formactions" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                            <tr class="prop">
                                <td valign="top" class="name">
									<g:if test="${edit}">
										<label for="name">name</label>
									</g:if>
									<g:else>
										name
									</g:else>
                                </td>
                                <td valign="top" class="value ${hasErrors('bean': instance, field: 'name', 'errors')}">
									<g:if test="${edit}">
										<input type="text" maxlength="50" id="name" name="name" value="${fieldValue('bean': instance, 'field': 'name')}"/>
									</g:if>
									<g:else>
										${instance?.name}
									</g:else>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
				<%-- Used in InstanceController/formactions --%>
                <input type="hidden" name="id" value="${instance?.id}" />
                <input type="hidden" name="mode" value="${mode}" />
                <div class="buttons">
					<g:if test="${edit}">
						<span class='button'><input class='save'  type='submit' name='operation' value='save'/></span>
					</g:if>
					<g:else>
						<span class='button'><input class='edit'  type='submit' name='operation' value='edit'/></span>
					</g:else>
					<g:if test="${mode != Constants.CREATE}">	<%-- Show the 'delete button only in 'show' or 'edit'. Not when creating a new instance --%>
						<span class='button'><input class='delete'  type='submit' name='operation' value='delete' onclick="return confirm('Delete, are you sure?');"/></span>
					</g:if>
						<span class='button'><input class='cancel' type='submit' name='operation' value='cancel'/></span>
                </div>
			</g:form>
        </div>
    </body>
</html>
```

---

in /views/Streettype/list.gsp
```
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
		<!-- Streettype/list.gsp -->
        <title>${instancename} List</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create">New ${instancename}</g:link></span>
        </div>
        <div class="body">
            <h1>${instancename} List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                   	        <th>name</th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${instancelist}" status="i" var="instance">
					  <%-- need a css class 'selected' to highlight the line like this: "tr.selected { background-color: #FFFFA0;	/* lightyellow; */ }" --%>
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}${(instance.id == selectedid) ? ' selected' : ''}">
                            <td class='streettype-name'><g:link action="show" id="${instance.id}">${instance.name}</g:link></td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
<%--
			<div class="paginateButtons">
                <g:paginate total="${instancetotal}" />
            </div>
--%>
		</div>
    </body>
</html>
```

---

in main.css add
```
tr.selected {
	background-color: #FFFFA0;	/* lightyellow; */
}
```

---