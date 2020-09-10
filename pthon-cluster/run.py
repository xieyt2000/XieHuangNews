import json
from collections import Counter
import requests

from sklearn.cluster import KMeans
from sklearn.feature_extraction.text import TfidfVectorizer


def loadDataset():
    with open("data.json", 'r', encoding='utf8') as f:
        data = json.load(f)
    text_list = [news['seg_text'] for news in data]
    return text_list, data


def save(dataset: list, predict: list, keywords: list):
    res = []
    for idx, news in enumerate(dataset):
        news.pop('seg_text')
        news.pop('type')
        news['cluster'] = int(predict[idx])
        news['keywords'] = keywords[predict[idx]]
        res.append(news)
    with open('res.json', 'w', encoding='utf8') as f:
        json.dump(res, f, ensure_ascii=False)


def transform(dataset, n_features):
    vectorizer = TfidfVectorizer(
        max_df=0.5, max_features=n_features, min_df=2, use_idf=True)
    X = vectorizer.fit_transform(dataset)
    return X, vectorizer


def train(X, vectorizer, n_clusters):

    km = KMeans(n_clusters=n_clusters, init='k-means++', max_iter=300, n_init=1,
                verbose=False)
    km.fit(X)
    order_centroids = km.cluster_centers_.argsort()[:, ::-1]
    terms = vectorizer.get_feature_names()
    keywords_list = []
    for i in range(n_clusters):
        keywords = []
        for ind in order_centroids[i, :4]:
            keywords.append(terms[ind])
        keywords_list.append(' '.join(keywords))
        
        print(f"Cluster {i}: {keywords_list[-1]}")

    result = list(km.predict(X))
    print('Cluster distribution:')
    print(Counter(result))
    return result, keywords_list


def main():
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

    with open('data.json', 'w', encoding='utf8') as file:
        json.dump(res_list, file)
    texts, dataset = loadDataset()
    X, vectorizer = transform(texts, n_features=300)

    predict, keywords = train(X, vectorizer, n_clusters=10)
    save(dataset, predict, keywords)


main()
