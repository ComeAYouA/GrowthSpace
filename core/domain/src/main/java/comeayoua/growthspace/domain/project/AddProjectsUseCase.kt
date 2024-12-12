package comeayoua.growthspace.domain.project

import comeayoua.growthspace.data.ProjectsRepository
import comeayoua.growthspace.domain.notification.SetNotificationUseCase
import comeayoua.growthspace.model.Project
import kotlinx.datetime.toKotlinLocalDateTime
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

class AddProjectsUseCase @Inject constructor(
    private val projectsRepository: ProjectsRepository,
    private val setNotificationUseCase: SetNotificationUseCase
) {
    suspend operator fun invoke(projects: List<Project>){
        projects.forEach { project ->
            if (project.remind) {
                project.schedule.value?.days?.forEach { dayOfWeek ->
                    val day = LocalDateTime.now().apply {
                        with(TemporalAdjusters.nextOrSame(DayOfWeek.of(dayOfWeek)))
                        withHour(23)
                        withMinute(this.minute + 1)
                    }
                    setNotificationUseCase.invoke(project.id, day.toKotlinLocalDateTime())
                }
            }
        }
        return projectsRepository.addProjects(projects)
    }
}