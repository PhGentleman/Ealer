from torchvision.models import ResNet
from torchvision.models.resnet import Bottleneck
from .csra import MHA
import torch.nn.functional as F


class ResNet_CSRA(ResNet):
    def __init__(self, num_heads, lam, num_classes, depth=101, input_dim=2048, cutmix=None):
        self.block, self.layers = [Bottleneck, (3, 4, 23, 3)]
        self.depth = depth
        super(ResNet_CSRA, self).__init__(self.block, self.layers)
        self.init_weights(pretrained=True, cutmix=cutmix)

        self.classifier = MHA(num_heads, lam, input_dim, num_classes) 
        self.loss_func = F.binary_cross_entropy_with_logits

    def backbone(self, x):
        x = self.conv1(x)
        x = self.bn1(x)
        x = self.relu(x)
        x = self.maxpool(x)

        x = self.layer1(x)
        x = self.layer2(x)
        x = self.layer3(x)
        x = self.layer4(x)

        return x

    def forward_test(self, x):
        x = self.backbone(x)
        x = self.classifier(x)
        return x

    def forward(self, x, target=None):
        if target is not None:
            return self.forward_train(x, target)
        else:
            return self.forward_test(x)
