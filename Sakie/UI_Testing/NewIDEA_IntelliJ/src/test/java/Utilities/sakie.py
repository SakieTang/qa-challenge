#! /usr/bin/python

import sys, traceback, requests, json, logging, hashlib, shutil
from urllib import urlencode
import os

HOST = 'https://flickr.com'
API = '/services/rest'

API_KEY    = '6df8e4c2fbd1e8b5b99f420105d883f5'
API_SECRET = '5326834e59e53d3e'
EMAIL      = 'tiqqqit@ymail.com'
PASSWORD   = 'Loyal30924'
AUTH       = False
USER_ID    = '1151157@N04'
LOG_LEVEL  = logging.INFO  # DEBUG

PROG_NAME  = os.path.split(sys.argv[0])[1]
LOG_FILE   = '%s.log' % PROG_NAME
LOG_STDOUT = True

DOWNLOAD_DIR = 'C:\downloads'

# The next 2 variables are only importatnt if authentication is used

# this can be set here or using flickr.tokenPath in your application
# this is the path to the folder containing tokenFile (default: token.txt)
tokenPath = ''

# this can be set here or using flickr.tokenFile in your application
# this is the name of the file containing the stored token.
tokenFile = 'token.txt'

logger = logging.getLogger(PROG_NAME)

def StartLogging():
    # Create log file:
    lh = logging.FileHandler(LOG_FILE)
    formatter = logging.Formatter('%(asctime)s %(levelname)s %(message)s')
    lh.setFormatter(formatter)
    logger.addHandler(lh) 

    # Print stdout for log message:
    if LOG_STDOUT:
        ch = logging.StreamHandler(sys.stdout)
        ch.setFormatter(formatter)
        logger.addHandler(ch)

    logger.setLevel(LOG_LEVEL)
    logger.info('Start logging ...')

def GetErrorTrace(e):
  return '%s:\n%s' % (str(e), traceback.print_exc())

def DoGet(method, auth=True, **params):
    sub = 'DoGet()'

    full_params = params
    full_params['format'] = 'json'
    full_params['nojsoncallback'] = 1
    full_params = _prepare_params(full_params)

    url = '%s%s/?api_key=%s&method=%s&%s%s'% \
          (HOST, API, API_KEY, method, urlencode(full_params),
                  _get_auth_url_suffix(method, auth, full_params))

    try:
      r =  requests.get(url)
      logger.debug('%s: r.text(%s)' % (sub, r.text))
      resp = r.json()
      logger.debug('%s: resp(%s)' % (sub, json.dumps(resp, indent=2)))
      if resp.get('code') == 98:
          GetAuthToken()     
          print 'Rerun your test'
          sys.exit(1)

    except Exception as e: 
      logger.error('%s: EXEPTION(%s)' % (sub, GetErrorTrace(e)))
      resp = {}

    return resp

def DoPost(method, auth=False, **params):
    #uncomment to check you aren't killing the flickr server
    #print "***** do post %s" % method

    params = _prepare_params(params)
    url = '%s%s/?api_key=%s%s'% \
          (HOST, API, API_KEY, _get_auth_url_suffix(method, auth, params))

    # There's no reason this can't be str(urlencode(params)). I just wanted to
    # have it the same as the rest.
    payload = '%s' % (urlencode(params))

    #another useful debug print statement
    if debug:
        print "_dopost url", url
        print "_dopost payload", payload
    
    return requests.get(url).json()


def DownloadImageFile(url, file):
    logger.info('DownloadImageFile(): url(%s), file(%s).')

    response = requests.get(url, stream=True)
    with open(file, 'wb') as f:
        shutil.copyfileobj(response.raw, f)



def GetPhotoUrl(photo_id):
    sub = 'GetPhotoUrl()'
    resp = DoGet('flickr.photos.getSizes', auth=True, photo_id=photo_id)
    for photo_info in resp['sizes']['size']:
       logger.debug('%s: photo_info(%s)' % (sub, photo_info))
       if photo_info['label'] == 'Original':
           url = photo_info['source']
           logger.info('%s: url(%s)' % (sub, url))
           return url   # TODO: add title

    logger.error('%s: No URL found.' % sub)
    return None

def GetAuthToken():
    sub = 'GetAuthToken()'

    auth = Auth()
    frob = auth.GetFrob()
    if not frob:
        logger.error('%s: failed to get frob.' % (sub, frob))
        sys.exit(1) 

    login_url = auth.LoginLink('write', frob)
    raw_input('Please authorize API by clicking the following URL: %s' % log_url)
    try:
        token = auth.GetToken(frob)['auth']['token']
    except Exception as e:
        logger.error('%s: EXCEPTION(%s)' % (sub, GetErrorTrace(e)))

    if token:
        f = open('token.txt', 'w')
        f.write(token)
        f.close()

    return token


class Auth():

    def GetFrob(self):
        sub = 'GetFrob()'

        """Returns a frob that is used in authentication"""
        method = 'flickr.auth.GetFrob'

        try:
            resp = DoGet(method, auth=False)
            frob = resp['frob']['_content']
            logger.info('%s: frob(%s)' % (sub, frob))
        except Exception as e:
            logger.error('%s: EXEPTION(%s)' % (sub, GetErrorTrace(e)))
            return None

    def LoginLink(self, permission, frob):
        """Generates a link that the user should be sent to"""
        sig_str = API_SECRET + 'api_key' + API_KEY + 'frob' + frob + 'perms' + permission
        signature_hash = hashlib.md5(sig_str).hexdigest()
        perms = permission
        link = "http://flickr.com/services/auth/?api_key=%s&perms=%s&frob=%s&api_sig=%s" % (API_KEY, perms, frob, signature_hash)
        return link

    def GetToken(self, frob):
        """This token is what needs to be used in future API calls"""
        sub = 'GetToken()'
        method = 'flickr.auth.GetToken'
        resp = DoGet(method, auth=False, frob=frob)

        try:
            return resp
        except Exception as e:
            logger.error('%s: EXEPTION(%s)' % (sub, GetErrorTrace(e)))
            return None


#------------ Private Routines ---------------------------------#
def _prepare_params(params):
    """Convert lists to strings with ',' between items."""
    for (key, value) in params.items():
        if isinstance(value, list):
            params[key] = ','.join([item for item in value])
    return params

def _get_api_sig(params, auth):
    """Generate API signature."""
    sub = '_get_api_sig()'

    if auth:
        parameters = ['api_key', 'auth_token']
    else:
        parameters = ['api_key']

    for item in params.items():
        parameters.append(item[0])
    parameters.sort()

    api_string = [API_SECRET]

    for item in parameters:
        for chocolate in params.items():
            if item == chocolate[0]:
                api_string.append(item)
                api_string.append(str(chocolate[1]))
        if item == 'api_key':
        	api_string.append('api_key')
        	api_string.append(API_KEY)
        if item == 'auth_token':
            api_string.append('auth_token')
            api_string.append(AUTH_TOKEN)

    logger.debug('%s: api_string(%s).'  % (sub,api_string))
    api_signature = hashlib.md5(''.join(api_string)).hexdigest()

    return api_signature


def _get_auth_url_suffix(method, auth, params):
    full_params = params
    full_params['method'] = method

    print '++full_params:', full_params
    api_sig = _get_api_sig(full_params, auth)

    if auth:
        return '&auth_token=%s&api_sig=%s' % (AUTH_TOKEN, api_sig)
    else:
        return '&api_sig=%s' %  api_sig

           
if __name__ == '__main__':


    if len(sys.argv) > 1:
        min_date = sys.argv[1]
    else:
        min_date = '1/1/2015'

    StartLogging()

    if not os.path.isdir(DOWNLOAD_DIR):
        os.makedirs(DOWNLOAD_DIR)

    if os.path.isfile('token.txt'):
       AUTH_TOKEN = open('token.txt').read()
    else:
       AUTH_TOKEN = GetAuthToken()

    # users = DoGet('flickr.tags.getListUser', auth=True)
    photos = DoGet('flickr.photos.recentlyUpdated', auth=True, min_date=min_date)

    for file in os.listdir(DOWNLOAD_DIR):
        os.system('del /F %s\%s' % (DOWNLOAD_DIR, file))

    test_pass = True
    for photo_info in photos['photos']['photo']:
       photo_id = photo_info['id']
       #(url, title) = GetPhotoUrl(photo_id)
       url = GetPhotoUrl(photo_id)
       DownloadImageFile(url, '%s/%s' % (DOWNLOAD_DIR, url.split('/')[-1]))
       #import re
       #title = re.sub(r'^sakie-', '', title)
       #DownloadImageFile(url, '%s/%s' % (DOWNLOAD_DIR, title))
    #not finish
    # if not test_pass: sys.exit(1)
