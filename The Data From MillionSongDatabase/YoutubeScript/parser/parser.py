import urllib.request
import contextlib
import time
from bs4 import BeautifulSoup
import csv
# song:id to url
map_id_to_url = {}

with open('songs.csv') as f:
    reader = csv.DictReader(f)
    try:
        for i, row in enumerate(reader):
            if i == 96:
                break
            name = row['ArtistName'][1:]
            title = row['Title'][1:]
            song_id = row['SongID']
            print(name, title, song_id)
            text_to_search = '{} - {}'.format(name, title)
            query = urllib.parse.quote(text_to_search)
            url = "https://www.youtube.com/results?search_query=" + query
            with contextlib.closing(urllib.request.urlopen(url)) as response:
                html = response.read()
                soup = BeautifulSoup(html, 'html.parser')
                for ind, vid in enumerate(soup.findAll(attrs={'class': 'yt-uix-tile-link'})):
                    if ind == 1:
                        break
                    map_id_to_url[song_id] = str("https://www.youtube.com" + vid['href']).encode('utf-8')
            # time.sleep(60)
    except Exception:
        print("Busted")
with open('urls.csv', 'a', newline='') as f:
    fieldnames = ['SongID', 'SongURL']
    writer = csv.DictWriter(f, fieldnames=fieldnames)
    writer.writeheader()
    for song_id, song_url in map_id_to_url.items():
        writer.writerow({'SongID': song_id, 'SongURL': song_url})

