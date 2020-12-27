# My Art Projects

## Dynamic Word Cloud

The **Dynamic Word Cloud** generates a GUI based on the words in a given text file. The relative font size of each word in the GUI correlates to its frequency within the text file. Each word is assigned a random color, a random initial position, and a random velocity.

#### Run it yourself:
1. Create a copy of [DynamicWordCloud.java](https://github.com/k-mcc/art/blob/main/DynamicWordCloud/DynamicWordCloud.java) (and sampleText.txt if you want to use it) on your device.

2. In DynamicWordCloud.java, replace `sampleText.txt` (line 25) with the name of a .txt file in the main method.
```java
String fileName = "sampleText.txt";
```

3. Run DynamicWordCloud.java

## Dynamic Lyric Cloud
### *What music looks like*

Dynamic Lyric Cloud can connect to the Genius Lyrics API to generate the same visuals for song lyrics.

***How It Works:** lyrics.py takes the names of musical artists and writes the lyrics of their three most popular songs into sampleText.txt. Then, DynamicLyricCloud.java reads from sampleText.txt and generates a dynamic cloud of the words in the fetched lyrics, organized by their frequency.*

#### Run It Yourself:
1. Navigate to the Songs folder through the DynamicWordCloud folder.

2. Copy or download DynamicLyricCloud.java, lyrics.py, and sampleText.txt into a folder on your device.

3. Assuming python3 and pip are already installed and up-to-date on your device, install the latest version of [lyricsgenius](https://lyricsgenius.readthedocs.io/en/master/) (a Python client for the Genius.com API) from GitHub through this command:

`pip3 install --user git+https://github.com/johnwmillr/LyricsGenius.git`

4. Get API Key from Genius Lyrics
    1. [Sign up](https://genius.com/signup_or_login) for a Genius Lyrics account.
    2. [Create a new API client](https://genius.com/api-clients) (while logged into account). Fill out the “App Name” field and “App Website URL” field (any name and any URL will work), leaving the rest of the fields blank. Click “Save.”
    3. Under “Client Access Token,” click “Generate Access Token” and copy the token provided.
    4. Paste this token between the quotes that read ‘API Key’ on line 5 of lyrics.py.
```python
geniusLyrics = lyricsgenius.Genius('API Key', # unique Genius Lyrics API key
```

5. **Run lyrics.py** to fill sampleText.txt with song lyrics (you can specify which artists and how many songs each in lyrics.py). Then, **run DynamicLyricCloud.java** to generate a dynamic visual of sampleText.txt.


---

## Painting

Use Painting.java and Paintbrush.java to paint with different colors on a GUI canvas.
