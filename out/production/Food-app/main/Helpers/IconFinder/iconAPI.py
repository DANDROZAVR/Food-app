from PIL import Image
import requests
import io
import sys


BASE_ENDPOINT = 'https://api.iconfinder.com/v4/'
API_SECRET    = "ql5epLrO1hVeHpVeLuikCGpLInsyYcffPZBcng6zerbG7SDFwUdqxNwEMRWChQ1d" # Keep this secret

def download_icons(product_name):
  querystring = {"query":product_name,
                 "count":"1",
                 "premium":"false"}
  url = BASE_ENDPOINT + "icons/search"
  headers = {
    "Accept": "application/json",
    "Authorization": "Bearer " + API_SECRET
  }
  response = requests.request("GET", url, headers=headers, params=querystring)
  if response.json()["total_count"] == 0:
    print(product_name + "total_count=0")
    return
  apiInfo = response.json()["icons"][0]["raster_sizes"]
  styles = apiInfo[len(apiInfo) - 1]
  image = styles["formats"][0]
  if image["format"] != "png":
    print(product_name + "unsupportable format")
    return
  download_url = image["download_url"]
  download_response = requests.request("GET", download_url, headers=headers)
  in_memory_file = io.BytesIO(download_response.content)
  im = Image.open(in_memory_file)
  path = sys.argv[0].rsplit('/',1)
  im.save(path[0] + "/../../../resources/Icons/" + product_name + ".png")
  im.save(path[0] + "/../../../../out/production/Food-app/resources/Icons/" + product_name + ".png")


for index in range(1, len(sys.argv)):
  download_icons(sys.argv[index])

sys.exit(0)


