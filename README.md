Contact Broadcast Listenner
====

Background service that triggers broadscater from when a contact from your device is created or updated.
When a contact is created or updated the same triggers a broadcaster with contact data.


Permission Required
===
READ CONTACTS


How to use
===

```
declare var ContactBroadcsat: any;
```

```
ContactBroadcsat.eventContact("", success, failure);
```




