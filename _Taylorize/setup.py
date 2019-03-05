from setuptools import setup

setup(
   name = 'taylorize',
   version = '0.1.0',
   packages = ['taylorize'],
   entry_points = {
      'console_scripts': [
         'taylorize = taylorize.taylorize:main'
      ]
   }
)