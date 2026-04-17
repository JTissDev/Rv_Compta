# Structure algorithmique.

## Package *features.PCP.dto*

### classe : *Contacts*

> #### Fields
> **private** *long* id \
> **private** *String* name \
> **private** *String* email \
> **private** *String* mobile \
> **private** *String* company \
> **private** *String* role \
> **private** *String* address \
> **private** *String* zipCode \
> **private** *String* city \
> **private** *String* country \
> **private** *String* notes \
> 
> ***
> #### Constructors
> ***
> 
> **public Contacts()** Constructeur vide \
> **public Contacts (String name)** Associe un nom au contact \
> ``` this.setName(name)``` \
> **public Contacts (Long id, String name)** associe un id et un nom au contact 
> ``` 
> this (name)
> this.setId(id)
> ```
> 
> ***
> #### Getters
> ***
>
> **public *Long* getId()** ``` return this.Id ``` \
> **public *String* getName()** ``` return this.name ``` 
> 
> ***
> #### Setters
> ***
> 
> **public *void* setId(Long id)** ``` this.id=id ``` \
> **public *void* setName(String name)** ``` this.name=name ```
> 
> ***
> #### Methods
> ***
> 
> **public static *Contacts* FromJson(JsonObject json)**
> ```
> contact=new Contact()
> for each field in contact
> if json.containsKey(fieldKey) contact.setField(json.get(fieldKey default null))
> ```
> **public *String* toString()**
> ```
> stringBuilder
> for each field in this stringbuilder getfield.
> return stringBuilder
> ```
> **public *JSONObject* toJson()**
> ```
> builder
> foreach field in fields
> if field !+ null builder.add(fieldKey,field
> return builder
> ```