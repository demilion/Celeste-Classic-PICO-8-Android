# Celeste-Classic-PICO-8-Android
This is a step-by-step tutorial to use your PICO-8 cartridges on your Android phone, let's begin shall we?

*using google translate*


At the beginning of the year I started using PICO-8 when I found out that Celeste was born from such a simple concept.
The first thing I could discover using PICO is that you can export to html...

if you can use HTML you can visualize it directly on Android using a tool called WebView.

This is a step-by-step tutorial to use your PICO-8 cartridges on your Android phone, let's begin shall we?

What do we need:
- Android Studio(https://developer.android.com/studio/).
- Your PICO-8 html cartridge (html and js).

Now we need to understand what we are going to do:

1) create an application on Android (the classic helloworld: https://developer.android.com/training/basics/firstapp/).
2) Add the WebView tool to your app (https://jgvcodigo.blogspot.com/2017/12/webview-android.html).
3) Add the controls on the html file of your cartridge.
You can use the following template and call your .js file:

https://github.com/headjump/pico8_html_template (awesome morningtoast post: https://www.lexaloffle.com/bbs/?tid=30147)

4)Finally add your html and js file inside your android project:
`C: \ Users \ AndroidStudioProjects \ YOUR_PROYECT \ app \ src \ main \ assets`


This is the line that made the magic: 
**myWebView.loadUrl("file:///android_asset/YOUR_GAME.html");**
This line sends your html.

`MainActivity.java`
```

package com..;
import android.support.v7.app.AppCompatActivity;import android.os.Bundle;import android.webkit.WebView;


public class MainActivity extends AppCompatActivity {

    @Override    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        setContentView(R.layout.activity_main);
        WebView myWebView = (WebView) this.findViewById(R.id.webView);
        myWebView.loadUrl("file:///android_asset/YOUR_GAME.html");
    }
}

```
![alt text](https://www.lexaloffle.com/bbs/files/35039/pico_8_android.png)




So as you can see we are calling a simple html on android to be able to show our game. It seems the easiest way to have your first android application working.

And you don´t need as much knowledge to publish your Pico-8 game on Google play.


I leave my code so you can steal it ...

[![ko-fi](https://www.ko-fi.com/img/donate_sm.png)](https://ko-fi.com/V7V1R1YJ)

It has a couple of extra lines such as having ads or integrated purchases.

Let me know what you think!!!!!!
## https://apkpure.com/es/celeste-classic/com.demilion.a20

