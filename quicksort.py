

# 快速排序 传入列表 、开始位置和结束位置
def quick_sort(li, start, end):
    # 如果start和end碰头，说明要排的这个子数列就剩下一个数了，就不用排序了
    if not start<end:
        return

    # 拿出第一个数当做基准数mid
    mid = li[start]
    # low 标记左侧从基准数开始找比mid大的数的位置
    low = start
    # high 标记右侧end向左找比mid小的数的位置
    high = end

    # 我们要进行循环，只要low和high没有碰头就一直进行，当low和high相等说明碰头了
    while low < high:
        # 从high开始向左，找到第一个比mid小或者等于mid的数，标记位置（如果high的数比mid大，就左移high）
        while low<high and li[high]>mid:
            high -= 1
        # high所在的下标就是找到的右侧比mid小的数
        # 把找到的数放到左侧的空位 low 标记了这个空位
        li[low] = li[high]
        # 从low开始向右，找到第一个比mid大的数，标记位置，（如果low的数小于等于mid，我们就右移low）
        # 并且我们要确定找到之前，如果low和high碰头了则停止
        while low < high and li[low] <= mid:
            low += 1
        # 跳出循环后，low所在下标就是左侧比mid大的数所在位置
        # 我们把找到的数放在右侧空位上，high标记了这个空位
        li[high] = li[low]
        # 以上我们完成了一次 从右侧找到一个小数移到左侧，从左侧找到一个大数移动到右侧
    # 当这个while跳出来之后相当于low和high碰头了，我们把mid所在位置放在这个空位
    li[low] = mid
    # 这个时候mid左侧数均比mid小，右侧均比mid大
    # 然后对mid左侧所有数进行上述排序
    quick_sort(li, start, low - 1)
    # mid右侧所有数进行上述排序
    quick_sort(li, low+1, end)

if __name__ == '__main__':
    li = [3, 2, 0, 8, 10, 99, 47, 3, 2, 80]
    quick_sort(li, 0, len(li)-1)
    print(li)

