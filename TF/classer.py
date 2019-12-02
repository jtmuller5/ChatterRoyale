import tensorflow as tf
import tensorflow_hub as hub
import tensorflow_datasets as tfds

def importLibs():
    import tensorflow as tf
    import tensorflow_hub as hub
    import tensorflow_datasets as tfds

def versions():
    print("Version: ", tf.__version__)
    print("Eager mode: ", tf.executing_eagerly())
    print("Hub version: ", hub.__version__)
    print("GPU is", "available" if tf.config.experimental.list_physical_devices("GPU") else "NOT AVAILABLE")
    return


def loaddata():
    # Split the training set into 60% and 40%, so we'll end up with 15,000 examples
    # for training, 10,000 examples for validation and 25,000 examples for testing.
    train_validation_split = tfds.Split.TRAIN.subsplit([6, 4])

    (train_data, validation_data), test_data = tfds.load(
        name="imdb_reviews",
        split=(train_validation_split, tfds.Split.TEST),
        as_supervised=True)
