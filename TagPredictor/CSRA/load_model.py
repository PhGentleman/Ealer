import os
import pickle


class Model:
    def __init__(self):
        if os.path.exists('CSRA/model.pkl'):
            with open('CSRA/model.pkl', 'rb') as input:
                print("Loading model from {}".format("CSRA/model.pkl"))
                # 101 COCO num_heads=4, lam=0.5, num_classes=80
                self.model = pickle.load(input)
                print("Complete.")
        else:
            print("No model found.")

    def get(self):
        return self.model
