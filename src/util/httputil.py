import json
import requests
import urllib3

# Disable InsecureRequestWarning
urllib3.disable_warnings(urllib3.exceptions.InsecureRequestWarning)


# Function : https post
def https_post(url, data, auth_key) :

    _url = url
    _data = json.dumps(data)
    if (auth_key != ""):
        headers = {'Content-Type': 'application/json', 'kyoboApiKey':auth_key}
    else:
        headers = {'Content-Type': 'application/json'}

    try:
        response = requests.post(url, headers=headers, data=_data, verify=False)
        return response.json()

    except Exception as e:
        return e



if __name__ == '__main__':
    url = 'https://openapi.kyobo.co.kr:1443'
    uris = ['/v1.0/PAY/accident/receipt',
            '/v1.0/INS/design/guarantee',
            '/v1.0/INS/design/deposit',
            '/v1.0/COM/user/consumer',
            '/v1.0/INFO/user/husermapinfo',
            '/v1.0/PAY/loan/amount',
            '/v1.0/PAY/accident/progress',
            '/v1.0/BIL/resurrection/target',
            '/v1.0/BIL/income/deduction',
            '/v1.0/BIL/premium/prediction',
            '/v1.0/BIL/premium/calc',
            '/v1.0/VAL/fund/subscription',
            '/v1.0/VAL/insurance/variable',
            '/v1.0/PAY/accident/payment',
            '/v1.0/PAY/loan/principal',
            '/v1.0/PAY/withdraw/target',
            '/v1.0/PAY/refund/cancellation',
            '/v1.0/PAY/loan/acceptance',
            '/v1.0/PAY/dividend/payment',
            '/v1.0/PAY/loan/process',
            '/v1.0/PAY/insurance/life',
            '/v1.0/PAY/insurance/additive',
            '/v1.0/PAY/withdraw/payment',
            '/v1.0/NBI/contract/exception',
            '/v1.0/NBI/product/guarantee',
            '/v1.0/PAY/contract/terms']
    req_payloads = [
            {'dataBody': {'PN323UM_I_REG_NO': '5810172710637'}},
            {'dataBody' : {'PROD_NM' : '(무)교보프리미어종신보험Ⅲ','AGE' : '35','GENDER' : '남자','JUPI_NM' : '본인','NABIB_JUGI' : '월납','PREM_AMT' : '105700','JOIN_AMT' : '5000','INS_PERIOD' : '종신','PAY_PERIOD' : '20년납' }},
            {'dataBody' : {'PROD_NM' : '미리보는 내연금 무배당 교보변액연금보험Ⅱ', 'AGE' : '35', 'GENDER' : '남자','JUPI_NM' : '본인','ELAG_ST_AGE' : '60','NABIB_JUGI' : '월납','PREM_AMT' : '300000','JOIN_AMT' : '1000','INS_PERIOD' : '종신','PAY_PERIOD' : '20년납' }},
            {'userId':'kkimdoy', 'pwd':'1'},
            {'dataBody' : {'PRTY_REG_NO' : '6111081247131' }},
            {'dataBody' : {'PE601UM_I_PRTY_REG_NO' : '6302022521947'}},
            {'dataBody' : {'PN325UM_I_CLIM_CLIM_NO' : '20060803026973' }},
            {'dataBody' : {'YI021UM_I_INAG_NO' : '216023009510'}},
            {'dataBody' : {'YG041UM_I_INAG_INAG_NO' : '214124025374'}},
            {'dataBody' : {'YD171UM_I_REG_NO' : '5704152871038'}},
            {'dataBody' : {'YD091UM_I_PRTY_REG_NO' : '6006272613831'}},
            {'dataBody' : {'VL240UM_I_INAG_NO' : '15963258'}},
            {'dataBody' : {'VG932FS_I_INAG_NO' : '217065025183'}},
            {'dataBody' : {'PN065UM_I_PRTY_REG_NO' : '5604172829932'}},
            {'dataBody' : {'PE201UM_I_REG_NO' : '6205072501633'}},
            {'dataBody' : {'PC601UM_I_PRTY_REG_NO' : '7010082661032'}},
            {'dataBody' : {'PC101UM_I_PRTY_REG_NO' : '6103032205239'}},
            {'dataBody' : {'PB160UM_I_INAG_INAG_NO' : '212077020022','PB160UM_I_ACT_DT' : '2013-09-20'}},
            {'dataBody' : {'PB150UM_I_YEAR' : '2008','PB150UM_I_INAG_INAG_NO' : '191020589815','PB150UM_I_ACT_DT' : '2009-02-25'}},
            {'dataBody' : {'PB130UM_I_INAG_INAG_NO' : '201102366135','PB130UM_I_ACT_DT' : '2006-03-27'}},
            {'dataBody' : {'PB120UM_I_INAG_INAG_NO' : '198072870297','PB120UM_I_ACT_DT' : '2002-01-08'}},
            {'dataBody' : {'PB018UM_PRTY_NO' : '6301242190038'}},
            {'dataBody' : {'PB081UM_I_INAG_INAG_NO' : '205026021071'}},
            {'dataBody' : {'NZ280UM_ELAG_INAG_NO' : '197091040591'}},
            {'dataBody' : {'NF602UM_I_INAG_NO' : '214040020487'}},
            {'dataBody' : {'PB020UM_I_INAG_INAG_NO' : '200094117805'}}]

    auth_key = 'KVXbAdghO9CVLb9ZrrtNlN6Xy7WzGOgp'
    size = uris.__len__()
    print(size)
    for x in range(0, size):
        for loop in range(0, 50):
            responseData = https_post(url+uris[x], req_payloads[x], auth_key)
            print( x, "-", loop, "::", responseData)


