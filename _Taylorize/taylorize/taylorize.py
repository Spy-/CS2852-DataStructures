import os
import zipfile
from pyfiglet import Figlet

def zipDir(path, ziph):
   for root, dirs, files in os.walk(path):
      for file in files:
         if root.count('src') > 0 and (file.endswith('.java') or file.endswith('.fxml')):
            file_path = os.path.join(root, file)
            ziph.write(file_path, 'bretz/'+os.path.basename(file_path))
         elif file.endswith('.jar'):
            file_path = os.path.join(root, file)
            ziph.write(file_path, os.path.basename(file_path))

def main():
   print(Figlet(font='slant').renderText('taylorize'))

   if not os.path.exists("_Submitals"):
      os.mkdir("_Submitals")

   while True:
      project = input("What is the project's name\n")

      try:
         project = [i for i in next(os.walk('.'))[1] if i.lower().count('lab')>0][int(project)]
      except:
         pass
      
      if os.path.exists(project):
         print('Creating zip...')

         zipf = zipfile.ZipFile(f"_Submitals/{project}.zip", 'w', zipfile.ZIP_DEFLATED)
         zipDir(project, zipf)
         zipf.close()

         print('Zip file created')
         break
      else:
         print(f"Valid projects are: {[i for i in next(os.walk('.'))[1] if i.lower().count('lab')>0]}")

if __name__ == "__main__":
   main()