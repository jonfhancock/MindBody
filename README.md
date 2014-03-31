MindBody Sample
========
I set out to write an app that:
* Looks nice
* Uses a responsive design
* Shows both staff and appointments in a nice way
* Handles offline mode
* Is not prone to crashes
* Supports 98% of the Android devices active today (Gingerbread and up)
* Goes above and beyond the minimum viable product

Download the apk here: https://github.com/jonfhancock/MindBody/blob/master/MindBodyHancock-debug.apk?raw=true

Dealing with SOAP
-------
My first  hurdle was dealing with SOAP.  I've mostly used RESTfull APIs with JSON.  I did work with the Google Checkout Notifications API in XML, but that was in PHP, and a few years ago.

I quickly saw that I would either have to find a library to marshal and unmarshal xml or write all the parsing code myself.  I am fond of having POJOs to work with, so I went looking for a library.

I found a libray called icesoap that worked perfectly for me at https://github.com/AlexGilleran/IceSoap.
It allowed me to specify the SOAP envelopes and actions I wanted to use, and what Java classes I wanted it to be marshalled to.


POJO Boilerplate
-------
I very much dislike writing tedious boilerplate code, so some time ago, I built an application that can read a JSON stream and output Java classes including annotations for gson, and having each class implement Parcelable, a very useful interface for Android data models.  https://github.com/jonfhancock/JsonToJava

I converted the xml responses from the API to json, and ran them through my code generator, and the result required only a few tweaks to be useable with icesoap.

ContentProviders
-------
Having the Pojos was great, but it left me with making API calls after user interaction, which can appear slow to the user, and would make the app useless offline. So I turned to a ContentProvider to store the data locally and load it into my views instantly rather than waiting for API calls to finish.

That means more tedious boilerplate code, so I turned to a project by a friend at Google to generate the content provider code on my behalf given a json configuration file. https://github.com/foxykeep/ContentProviderCodeGenerator

SyncAdapter
-------
That was the persistence step, but I still need to actually pull the data down.  For that, I used a SyncAdapter.  It is an underutilized piece of the Android Framework mostly because it is complicated to implement.  Once done though, it can run in the background either at intervals, upon request, or as a response to a push notification.  This allowed me to populate my local data immediately upon first run, and again whenever the refresh button is clicked.

What is more, since I am using a ContentProvider, when the SyncAdapter runs and updates the local data, my views are notified, and will refresh themselves.

CursorLoaders, CursorAdapters and ListFragments
-------
To display the content, I used ListFragments driven by default CursorLoaders.  Then Staff and Appointments get separate treatment with a CursorAdapter.  This makes it extremely easy to listen for changes to the ContentProvider so the user always sees the latest data.

From Gingerbread on up
-------
I initially built the app to support only Ice Cream Sandwich and up, but once I was done, I went back and implemented the AppCompat library from Google so that it now works on Gingerbread and Honeycomb devices as well.

Responsive Design
-------
On phones, the staff list takes up the full screen, and when an item is clicked, it will fill the screen with that staff member's appointments.

On tablets where we have more space, I maintain a master detail layout where the list of staff members is on the left, and clicking one makes their appointments appear on the right.

A sensible theme
-------
I used the ActionBar Style Generator here: http://jgilfelt.github.io/android-actionbarstylegenerator/
This allowed me to quickly build an orange theme to roughly match the MindBody look.

Extras
-------
I decided to give some of the staff members photo avatars so that I could display them in the app.  I show an image, if available, next to their names.

To do that with very little code, I used Square's Picasso library: http://square.github.io/picasso/

The results
-------
* This app looks nice on handsets and tablets from Gingerbread to KitKat.  
* It syncs data in the background very quickly, and efficiently.  
* It supports orientation changes (I'm saddened by how many apps lock users into portrait mode).  
* It works offline, although due to cache limitations defined on the server, which Picasso respects, photos won't load offline.
* It meets all of the requirements plus a few extra bonuses. 
