import sqlite3
import hashlib
import base64

ADMIN_PASSWORD = b'Q5wxkCveGkzaOBB8a0MFCuD9zNtrqg1FCemn5y1YXckl66/zgsqIDSbSdvrKx6Pfqcw3UybudajiYAHJmKRGrebbePY0LXv3PyLE7+paCz3I/XhNg2DGBxUDsb+CDYzaq6vgZMfX2FuAVm0zb8UksR0+sGDVAyI3GS3VgYRx4DY=' 

SALT = b'$\xb6V\xcc\xd5C\x9d\xa4\x87j\xcbX\xacJ\x08\xf6Z\x00\x88\xaaq\x9e\xa2N\x1bQ\xa9\xd5j\xa2\xf7\x05'
conServer = sqlite3.connect('usersDetails.db')

def encryptPassword(password):
    key = hashlib.pbkdf2_hmac(
        'sha256', 
        password.encode('utf-8'), 
        SALT,
        100000,
        dklen=128
    )
    return base64.b64encode(key)

def createTable(username):
    cur = conServer.cursor()
    if encryptPassword(username) == ADMIN_PASSWORD:
        cur.execute('''CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY, username text, password text)''')
        print('------ USER DETAILS TABLE CREATED ------')
    cur.close()

def wipeTable(username):
    cur = conServer.cursor()
    if encryptPassword(username) == ADMIN_PASSWORD:
        cur.execute('''DROP TABLE users''')
        conServer.commit()
        print('------ USER DETAILS TABLE WIPED ------')
    cur.close()


def isTableSetup():
    cur = conServer.cursor()
    cur.execute(""" SELECT COUNT(*) FROM information_schema.tables 
                WHERE table_name = '{0}'
                """.format("users".replace('\'', '\'\'')))
    if cur.fetchone()[0] == 1:
        cur.close()
        return True
    cur.close()
    return False


def checkCredentials(username, password):
    cur = conServer.cursor()
    for passwordFound in cur.execute('SELECT password FROM users WHERE username= ?', (username,)):
        if passwordFound[0] == encryptPassword(password):
            cur.close()
            print(True)
            return True
    cur.close
    print(False)
    return False

def userExisits(username):
    cur = conServer.cursor()
    return cur.execute('SELECT username FROM users WHERE username= ?', (username,)).fetchone() != None

def addUser(username, password):
    if not userExisits(username):
        cur = conServer.cursor()
        cur.execute('INSERT INTO users(username, password) VALUES (?, ?)', (username, encryptPassword(password)))
        conServer.commit()
        cur.close()
        print(True)
        return True
    else:
        print(False)
        return False

def viewTable():
    cur = conServer.cursor()
    try:
        hasUsers = False
        for row in cur.execute('SELECT * FROM users'):
            hasUsers = True
            print(row)
        print(("---- TABLE EMPTY ----", "")[hasUsers])
    except:
        print('------ USER DETAILS TABLE NOT CREATED ------')
        if input('-- SET UP TABLE? (Y/N) ') == 'Y':
            createTable(input("-- ENTER PASSWORD: "))
