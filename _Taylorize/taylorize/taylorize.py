import os
import zipfile
from pyfiglet import Figlet

def printProgressBar (iteration, total, prefix = '', suffix = '', decimals = 1, length = 100, fill = '█'):
    percent = ("{0:." + str(decimals) + "f}").format(100 * (iteration / float(total)))
    filledLength = int(length * iteration // total)
    bar = fill * filledLength + '-' * (length - filledLength)
    print('\r%s |%s| %s%% %s' % (prefix, bar, percent, suffix), end = '\r')
    # Print New Line on Complete
    if iteration == total: 
        print()

def zipDir(path, ziph):
    hadJar = False
    for root, dirs, files in os.walk(path):
        for file in files:
            if root.count('src') > 0 and (file.endswith('.java') or file.endswith('.fxml')):
                file_path = os.path.join(root, file)
                ziph.write(file_path, 'bretz/'+os.path.basename(file_path))
            elif file.endswith('.jar'):
                file_path = os.path.join(root, file)
                ziph.write(file_path, os.path.basename(file_path))
                hadJar = True
            elif file.endswith('.md') and not file.lower().count('readme'):
                file_path = os.path.join(root, file)
                ziph.write(file_path, os.path.basename(file_path))

    return hadJar
                
def main():
    print('Generating Submitalls...')

    if not os.path.exists('_Submitals'):
        os.mkdir('_Submitals')

    projects = [i for i in next(os.walk('.'))[1] if i.lower().count('lab')>0]
    properProject = len(projects)

    printProgressBar(0, len(projects), prefix = 'Progress:', suffix = 'Complete', length = 50)

    for i, project in enumerate(projects):
        zipf = zipfile.ZipFile(f"_Submitals/{project}.zip", 'w', zipfile.ZIP_DEFLATED)
        if not zipDir(project, zipf):
            properProject -= 1
        zipf.close()
        printProgressBar(i + 1, len(projects), prefix = 'Progress:', suffix = 'Complete', length = 50)

    print(f"{properProject}/{len(projects)} projects were properly created")


if __name__ == "__main__":
    main()
