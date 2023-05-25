from flask import *
import sys
PYTHONPATH = r"CSRA"
sys.path.append(PYTHONPATH)
from predictor import Predictor

app = Flask(__name__)

# 预加载变量:
# ip白名单
ip_whitelist = ["127.0.0.1"]
# 加载预测器
predictor = Predictor()


# 映射:
# 工作状态
@app.route('/')
def home():
    return '<h1 style=\'text-align: center;\'>TagPredictor is working.</h1>'


@app.route('/locTest')
def test():
    return render_template('test.html')


# 上传图像执行预测
@app.route('/upload', methods=['POST'])
def upload():
    ip = request.remote_addr
    if ip in ip_whitelist:
        file = request.files['picture']
        if file is not None:
            return predictor.predict_tags(file)
        else:
            return "no file!"
    else:
        print("illegal ip from: " + ip)
        return "illegal ip"


if __name__ == '__main__':

    app.run(host="0.0.0.0", port=5000, debug=True)
