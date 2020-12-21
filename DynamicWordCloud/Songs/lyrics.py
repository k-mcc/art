import lyricsgenius

textFile = open("/Users/katemccarthy/Documents/GitHub/art/DynamicWordCloud/sampleText.txt", "w") # specify directory path to create/modify file

geniusLyrics = lyricsgenius.Genius('zMdfaMf33TgkUQygbQN83TxLm0r_3blHu9azr5W-czo3h_gF_g9rDdS7G_eF1VQl', # unique Genius Lyrics API key
                         skip_non_songs=True, excluded_terms=["(Remix)", "(Live)"],
                         remove_section_headers=True)

artists = ['BROCKHAMPTON'] # artists to fetch lyrics from


def fetch_lyrics(artists, numSongs):  # fetch lyrics of popular songs (quantity k) of artists
    songsFetched = 0  # total number of songs fetched
    for artist in artists:
        try:
            songs = (geniusLyrics.search_artist(artist, max_songs=numSongs, sort='popularity')).songs
            lyrics = [song.lyrics for song in songs]
            textFile.write("\n \n   <|endoftext|>   \n \n".join(lyrics)) # pushes song lyrics to the text file
            songsFetched += 1
            print(f"Songs fetched : {len(s)}")
        except:
            print(f"some exception at {artist}: {songsFetched}")



fetch_lyrics(artists, 10)
