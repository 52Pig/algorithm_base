import numpy as np
import tensorflow as tf

class BubbleSort():
    def __init__(self, array):
        self.i = tf.constant(0)
        self.j = tf.constant(len(array)-1)
        self.array = tf.Variable(array, trainable=False)
        self.length = len(array)
        cond = lambda i, j, _: tf.less(i-1, self.length-1)
        self.graph = tf.while_loop(cond, self.outer_loop, loop_vars=[self.i, self.j, self.array])

    def run(self):
        with tf.Session() as sess:
            tf.global_variables_initializer().run()
            return sess.run(self.graph)

    def outer_loop(self, i, j, _):
        cond = lambda i, j, _: tf.greater(j, i)
        loop = tf.while_loop(cond, self.inner_loop, loop_vars=[i, self.length-1, self.array])
        return tf.add(i, 1), loop[1], loop[2]

    def inner_loop(self, i, j, _):
        body = tf.cond(tf.greater(self.array[j-1], self.array[j]),
                       lambda: tf.scatter_nd_update(self.array, [[j-1],[j]], [self.array[j],self.array[j-1]]),
                       lambda: self.array)
        return i, tf.subtract(j, 1), body

if __name__ == '__main__':
    x = np.array([1.,7.,3.,8.])
    _, _, sorted_array = BubbleSort(x).run()
    print(x)
    print(sorted_array)