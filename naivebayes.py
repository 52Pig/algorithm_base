#encoding:UTF-8
from numpy import *
import pickle
import jieba
import time

stop_word = []
'''
    停用词集, 包含“啊，吗，嗯”一类的无实意词汇以及标点符号
'''
def loadStopword():
    fr = open('stopword.txt', 'r')
    lines = fr.readlines()
    for line in lines:
        stop_word.append(line.strip().decode('utf-8'))
    fr.close()
        
'''
    创建词集
    params:
        documentSet 为训练文档集
    return:词集, 作为词袋空间
'''
def createVocabList(documentSet):
    vocabSet = set([])
    for document in documentSet:
        vocabSet = vocabSet | set(document) #union of the two sets
    return list(vocabSet)
    
'''
    载入数据
'''
def loadData():
    return None
    
'''
   文本处理，如果是未处理文本，则先分词（jieba分词）,再去除停用词
'''
def textParse(bigString, load_from_file=True):    #input is big string, #output is word list
    if load_from_file:
        listOfWord = bigString.split('/ ')
        listOfWord = [x for x in listOfWord if x != ' ']
        return listOfWord
    else:
        cutted = jieba.cut(bigString, cut_all=False)
        listOfWord  = []
        for word in cutted:
            if word not in stop_word:
                listOfWord.append(word)
        return [word.encode('utf-8') for word in listOfWord]
        
'''
    交叉训练
'''
CLASS_AD        = 1
CLASS_NOT_AD    = 0

def testClassify():
    listADDoc = []
    listNotADDoc = []
    listAllDoc = []
    listClasses = []
    
    print "----loading document list----"
    
    #两千个标注为广告的文档
    for i in range(1, 1001):
        wordList = textParse(open('subject/subject_ad/%d.txt' % i).read())
        listAllDoc.append(wordList)
        listClasses.append(CLASS_AD)
    #两千个标注为非广告的文档
    for i in range(1, 1001):
        wordList = textParse(open('subject/subject_notad/%d.txt' % i).read())
        listAllDoc.append(wordList)
        listClasses.append(CLASS_NOT_AD)
    
    print "----creating vocab list----"    
    #构建词袋模型
    listVocab = createVocabList(listAllDoc)
    
    docNum = len(listAllDoc)
    testSetNum  = int(docNum * 0.1);
    
    trainingIndexSet = range(docNum)   # 建立与所有文档等长的空数据集（索引）
    testSet = []                       # 空测试集
    
    # 随机索引，用作测试集, 同时将随机的索引从训练集中剔除
    for i in range(testSetNum):
        randIndex = int(random.uniform(0, len(trainingIndexSet)))
        testSet.append(trainingIndexSet[randIndex])
        del(trainingIndexSet[randIndex])
    
    trainMatrix = []
    trainClasses = []
   
    for docIndex in trainingIndexSet:
        trainMatrix.append(bagOfWords2VecMN(listVocab, listAllDoc[docIndex]))
        trainClasses.append(listClasses[docIndex])
    
    print "----traning begin----"
    pADV, pNotADV, pClassAD = trainNaiveBayes(array(trainMatrix), array(trainClasses))
    
    print "----traning complete----"
    print "pADV:", pADV
    print "pNotADV:", pNotADV
    print "pClassAD:", pClassAD
    print "ad: %d, not ad:%d" % (CLASS_AD, CLASS_NOT_AD)
    
    args = dict()
    args['pADV'] = pADV
    args['pNotADV'] = pNotADV
    args['pClassAD'] = pClassAD
    
    fw = open("args.pkl", "wb")
    pickle.dump(args, fw, 2)
    fw.close()
    
    fw = open("vocab.pkl", "wb")
    pickle.dump(listVocab, fw, 2)
    fw.close()

    errorCount = 0
    for docIndex in testSet:
        vecWord = bagOfWords2VecMN(listVocab, listAllDoc[docIndex])
        if classifyNaiveBayes(array(vecWord), pADV, pNotADV, pClassAD) != listClasses[docIndex]:
            errorCount += 1
            doc = ' '.join(listAllDoc[docIndex])
            print "classfication error", doc.decode('utf-8', "ignore").encode('gbk')
    print 'the error rate is: ', float(errorCount) / len(testSet)
        
# 分类方法(这边只做二类处理)
def classifyNaiveBayes(vec2Classify, pADVec, pNotADVec, pClass1):
    pIsAD = sum(vec2Classify * pADVec) + log(pClass1)    #element-wise mult
    pIsNotAD = sum(vec2Classify * pNotADVec) + log(1.0 - pClass1)
    
    if pIsAD > pIsNotAD:
        return CLASS_AD
    else: 
        return CLASS_NOT_AD
    
'''
    训练
    params:
        tranMatrix 由测试文档转化成的词空间向量 所组成的 测试矩阵
        tranClasses 上述测试文档对应的分类标签
'''
def trainNaiveBayes(trainMatrix, trainClasses):
    numTrainDocs = len(trainMatrix)
    numWords = len(trainMatrix[0]) #计算矩阵列数, 等于每个向量的维数
    numIsAD  = len(filter(lambda x: x == CLASS_AD, trainClasses))
    pClassAD = numIsAD / float(numTrainDocs)
    pADNum = ones(numWords); pNotADNum = ones(numWords)
    pADDenom = 2.0; pNotADDenom = 2.0
    
    for i in range(numTrainDocs):
        if trainClasses[i] == CLASS_AD:
            pADNum += trainMatrix[i]
            pADDenom += sum(trainMatrix[i])
        else:
            pNotADNum += trainMatrix[i]
            pNotADDenom += sum(trainMatrix[i])
        
    pADVect = log(pADNum / pADDenom)
    pNotADVect = log(pNotADNum / pNotADDenom)
    
    return pADVect, pNotADVect, pClassAD
    
'''
    将输入转化为向量，其所在空间维度为 len(listVocab)
    params: 
        listVocab-词集
        inputSet-分词后的文本，存储于set
'''
def bagOfWords2VecMN(listVocab, inputSet):
    returnVec = [0]*len(listVocab)
    for word in inputSet:
        if word in listVocab:
            returnVec[listVocab.index(word)] += 1
    return returnVec
    
'''
    读取保存的模型，做分类操作
'''
def adClassify(text):
    fr = open("args.pkl", "rb")
    args = pickle.load(fr)
    pADV        = args['pADV']
    pNotADV     = args['pNotADV']
    pClassAD    = args['pClassAD']
    fr.close()

    fr = open("vocab.pkl", "rb")
    listVocab = pickle.load(fr)
    fr.close()
    
    if len(listVocab) == 0:
        print "got no args"
        return
        
    text = textParse(text, False)
    vecWord = bagOfWords2VecMN(listVocab, text)
    class_type = classifyNaiveBayes(array(vecWord), pADV, pNotADV, pClassAD)
        
    print "classfication type:%d" % class_type
    
    
if __name__ == "__main__":
    loadStopword()
    while True:
        opcode = raw_input("input 1 for training, 2 for ad classify: ")
        if opcode.strip() == "1":
            begtime = time.time()
            testClassify()
            print "cost time total:", time.time() - begtime
        else:
            text = raw_input("input the text:")
            adClassify(text)