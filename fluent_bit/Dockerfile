FROM amazon/aws-for-fluent-bit:2.16.1

COPY ./fluent-bit-custom.conf /fluent-bit/custom.conf
COPY ./parser.conf /fluent-bit/parser.conf
COPY ./stream_processor.conf /fluent-bit/stream_processor.conf

RUN ln -sf /usr/share/zoneinfo/Asia/Tokyo /etc/localtime
