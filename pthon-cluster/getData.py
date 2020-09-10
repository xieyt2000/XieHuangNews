import json
from pprint import pprint
import requests


BASE_URL = "https://covid-dashboard.aminer.cn/api/events/list?type=event&page=1&size=1000"

s = requests.get(BASE_URL)
data = s.json()['data']
print(len(data))
res_list = []
for event in data:
    event_dic = {
        'date': event['date'],
        'title': event['title'],
        'seg_text': event['seg_text'],
        'source': '', 'type': 'event'
    }
    res_list.append(event_dic)

# pprint(res_list)
with open('data.json', 'w', encoding='utf8') as file:
    json.dump(res_list, file)
