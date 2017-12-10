# -*- coding: utf-8 -*-
from matplotlib import pyplot
import scipy as sp
import numpy as np
from matplotlib import pylab
from sklearn.datasets import load_files
from sklearn.cross_validation import train_test_split
from sklearn.feature_extraction.text import  CountVectorizer
from sklearn.feature_extraction.text import  TfidfVectorizer
from sklearn.naive_bayes import MultinomialNB
from sklearn.metrics import precision_recall_curve, roc_curve, auc
from sklearn.metrics import classification_report
from sklearn.linear_model import LogisticRegression
import time

start_time = time.time()

#����R/P����
def plot_pr(auc_score, precision, recall, label=None):
    pylab.figure(num=None, figsize=(6, 5))
    pylab.xlim([0.0, 1.0])
    pylab.ylim([0.0, 1.0])
    pylab.xlabel('Recall')
    pylab.ylabel('Precision')
    pylab.title('P/R (AUC=%0.2f) / %s' % (auc_score, label))
    pylab.fill_between(recall, precision, alpha=0.5)
    pylab.grid(True, linestyle='-', color='0.75')
    pylab.plot(recall, precision, lw=1)    
    pylab.show()

#��ȡ
movie_data   = sp.load('movie_data.npy')
movie_target = sp.load('movie_target.npy')
x = movie_data
y = movie_target

#BOOL�������µ������ռ�ģ�ͣ�ע�⣬�����������õ���transform�ӿ�
count_vec = TfidfVectorizer(binary = False, decode_error = 'ignore',\
                            stop_words = 'english')
average = 0
testNum = 10
for i in range(0, testNum):
    #�������ݼ����з����ݼ�80%ѵ����20%����
    x_train, x_test, y_train, y_test\
        = train_test_split(movie_data, movie_target, test_size = 0.2)
    x_train = count_vec.fit_transform(x_train)
    x_test  = count_vec.transform(x_test)

    #ѵ��LR������
    clf = LogisticRegression()
    clf.fit(x_train, y_train)
    y_pred = clf.predict(x_test)
    p = np.mean(y_pred == y_test)
    print(p)
    average += p

    
#׼ȷ�����ٻ���
answer = clf.predict_proba(x_test)[:,1]
precision, recall, thresholds = precision_recall_curve(y_test, answer)    
report = answer > 0.5
print(classification_report(y_test, report, target_names = ['neg', 'pos']))
print("average precision:", average/testNum)
print("time spent:", time.time() - start_time)

plot_pr(0.5, precision, recall, "pos")