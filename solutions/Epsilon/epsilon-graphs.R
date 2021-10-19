#!/usr/bin/Rscript

library(ggplot2)
library(plyr)

data <- read.csv('../../output/output.csv', sep = ';')

epsilon <- data[data$Tool == 'Epsilon' & grepl('Nanos', as.character(data$MetricName), fixed=TRUE), ]
epsilon$Key <- paste0('S', epsilon$Stage, 'C', epsilon$Challenge)
epsilon$Nanos <- as.numeric(as.character(epsilon$MetricValue))
epsilon$Seconds <- epsilon$Nanos/1e9

epsilon$MetricName <- relevel(epsilon$MetricName, "TransformTimeNanos")
epsilon$MetricName <- relevel(epsilon$MetricName, "TransformTimeNanosWarmup")

epsilon$MetricName <- revalue(epsilon$MetricName, c(
  "TransformTimeNanos" = "2. Transform (avg over 10 runs)",
  "TransformTimeNanosWarmup" = "1. Transform (JVM warmup)",
  "TestTimeNanos" = "3. Query test"))

pdf('epsilon.pdf', width=6, height=4, useDingbats = FALSE)
(
  ggplot(epsilon, aes(x=Key, y=Seconds, fill=MetricName))
  + geom_bar(position="dodge", stat="identity")
  + scale_x_discrete(name="")
  + scale_y_continuous(breaks=seq(0,max(epsilon$Seconds),0.1))
  + theme(axis.text.x = element_text(angle=45,vjust=0.5),
          legend.title = element_blank(), legend.position = 'top')
)
dev.off()
