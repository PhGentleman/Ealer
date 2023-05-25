import load_model
import torch
import torch.nn as nn
from PIL import Image
from torchvision.transforms import transforms
from eval import coco_classes


class Predictor:
    def __init__(self):
        self.model = load_model.Model().get()

        normalize = transforms.Normalize(mean=[0, 0, 0], std=[1, 1, 1])
        img_size = 448

        # image pre-process
        self.transform = transforms.Compose([
            transforms.Resize((img_size, img_size)),
            transforms.ToTensor(),
            normalize
        ])

    # predict tags
    def predict_tags(self, img_file):
        # prediction of image's label
        img = Image.open(img_file).convert("RGB")
        img = self.transform(img)
        img = img.cuda()
        img = img.unsqueeze(0)

        self.model.eval()
        logit = self.model(img).squeeze(0)
        logit = nn.Sigmoid()(logit)

        pos = torch.where(logit > 0.5)[0].cpu().numpy()

        result = ''
        for k in pos:
            result += coco_classes[k]
            result += ','
            print(coco_classes[k], end=",")
        if result == '':
            return "none"
        return result
