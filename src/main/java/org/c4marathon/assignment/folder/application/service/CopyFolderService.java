package org.c4marathon.assignment.folder.application.service;

import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.file.application.port.out.FileCommandPort;
import org.c4marathon.assignment.file.application.service.FileSearchService;
import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.folder.application.port.in.CopyFolderUseCase;
import org.c4marathon.assignment.folder.application.port.in.MakeFolderUseCase;
import org.c4marathon.assignment.folder.application.port.out.FolderQueryPort;
import org.c4marathon.assignment.folder.domain.entity.Folder;
import org.c4marathon.assignment.user.domain.entity.User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CopyFolderService implements CopyFolderUseCase {

    private final FolderSearchService folderSearchService;

    private final FileSearchService fileSearchService;

    private final FolderQueryPort folderQueryPort;

    private final MakeFolderUseCase makeFolderUseCase;

    private final FileCommandPort fileCommandPort;

    /*
    1. 논리적으로 폴더만 먼저 모두 복사한다.
    2. 실제로 폴더만 전부 복사한다.
    3. 논리적 & 물리적으로 하나씩 파일을 전부 복사한다.
     */
    private void copyActualFolder(Folder folder, String copyPath) {
        Path source = Paths.get(folder.getPath());
        Path target = Paths.get(copyPath);

        try {
            Files.walkFileTree(source, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    Path targetDir = target.resolve(source.relativize(dir));
                    if (Files.notExists(targetDir)) {
                        Files.createDirectory(targetDir);
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.copy(file, target.resolve(source.relativize(file)), StandardCopyOption.REPLACE_EXISTING);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            throw new RuntimeException("Failed to copy folder", e);
        }
    }

    private String makeCopyString(User user, String folderName, Folder parentFolder) {
        String copy = " - Copy";
        while (true) {
            Optional<Folder> mustNotExist = folderQueryPort.findByUserAndFolderNameAndParentFolder(user, folderName + copy, parentFolder);
            if (mustNotExist.isPresent())
                copy += " - Copy";
            else
                break;
        }
        return copy;
    }

    private void copyLogicalFiles(User user, File source, Folder targetFolder) {
        File childFile = File.of(user, targetFolder, targetFolder.getPath() + "/" + source.getUuid() + "." + source.getType(), source.getUuid(), source.getFileName(), source.getType(), source.getSize());
        fileCommandPort.save(childFile);
    }


    private void copyLogicalFolders(User user, Folder copyFolder,  Folder copyParentFolder) {
        Folder copiedFolder = makeFolderUseCase.makeFolder(user, copyFolder.getFolderName(), copyParentFolder.getId());

        List<Folder> childFolders = folderSearchService.findAllSubElementsById(user, copyFolder.getId());
        for (Folder childFolder : childFolders) {
            copyLogicalFolders(user, childFolder, copiedFolder);
        }

        List<File> childFiles = fileSearchService.getFileListByFolder(user, copyFolder.getId());
        for (File childFile : childFiles) {
            copyLogicalFiles(user, childFile, copiedFolder);
        }
    }

    @Override
    public void copyFolder(User user, Long originFolderId) {
        Folder originFolder = folderSearchService.findById(user, originFolderId);
        String copyString = makeCopyString(user, originFolder.getFolderName(), originFolder.getParentFolder());
        String copyFolderName = originFolder.getFolderName() + copyString;
        String copyPath = originFolder.getPath() + copyString;

        // STEP1 : 논리적으로 폴더를 모두 복사한다.
        Folder copiedFolder = makeFolderUseCase.makeFolder(user, copyFolderName, originFolder.getParentFolder() == null ? null : originFolder.getParentFolder().getId());

        List<Folder> childFolders = folderSearchService.findAllSubElementsById(user, originFolderId);
        for (Folder childFolder : childFolders) {
            copyLogicalFolders(user, childFolder, copiedFolder);
        }

        // STEP2 : 논리적으로 파일을 모두 복사한다.
        List<File> childFiles = fileSearchService.getFileListByFolder(user, originFolderId);
        for (File childFile : childFiles) {
            copyLogicalFiles(user, childFile, copiedFolder);
        }

        // STEP3 : 실제로 전부 복사한다.
        copyActualFolder(originFolder, copyPath);
    }

}
