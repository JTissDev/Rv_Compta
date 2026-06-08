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
> **public Contacts (String name)** Associe un name au contact \
> ``` this.setName(name)``` \
> **public Contacts (Long id, String name)** associe un id et un name au contact 
> ``` 
> this (name)
> this.setId(id)
> ```
> 
> ***
> #### Getters
> ***
>
> For each Field : ```getFieldName() return this.field```
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
>

### classe : *Tiers*

> #### Fields
> **private** *Long* id \
> **private** *String* name \
> **private** *String* thirdPartyType \
> **private** *String* description 
>
> ***
> #### Constructors
> ***
> 
> **public Tiers()** Constructeur Vide \
> **public Tiers(String name)** ``` this.setName(name)``` \
> **public Tiers(String name, String thirdPartyType)** ``` this(name),this.setThirdPartyType(thirdPartyType)``` \
> **public Tiers(String name, String thirdPartyType, String description)** ```this(name, thirdPartyType), this.setDescription(description)``` \
> **public Tiers(Long id, String name)** ``` this(name), this.setId(id) ``` \
> **public Tiers(Long id, String name, String thirdPartyType)** ``` this(name, thirdPartyType), this.setId(id) ``` \
> **public Tiers(Long id, String name, String thirdPartyType, String description)** ```this(name, thirdPartyType, description), this.setId(id) ``` 
>
> ***
> #### Getters
> ***
> 
> For each Field : ```getFieldName() return this.field```
>
> ***
> #### Setters
> ***
>
> **public *void* setId(Long id)** ``` this.id=id ``` \
> **public *void* setName(String name)** ``` this.name=name ``` \
> **public *void* setDescription(String description)** ```this.description=description``` \
>
> ***
> #### Methods
> ***
>
> **public static *Tiers* FromJson(JsonObject json)**
> ```
> tiers=new Tiers()
> for each field in tiers
> if json.containsKey(fieldKey) tiers.setField(json.get(fieldKey default null))
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
>

### classe : *Tiers*

> #### Fields